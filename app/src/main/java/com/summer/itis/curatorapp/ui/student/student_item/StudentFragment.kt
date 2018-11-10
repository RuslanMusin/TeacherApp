package com.summer.itis.curatorapp.ui.student.student_item

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.user.Student
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragment
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationBaseActivity.Companion.SHOW_THEMES
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationView
import com.summer.itis.curatorapp.ui.curator.curator_item.description.view.DescriptionFragment
import com.summer.itis.curatorapp.ui.skill.skill_list.view.SkillListFragment
import com.summer.itis.curatorapp.ui.theme.add_theme.AddThemeFragment
import com.summer.itis.curatorapp.ui.theme.add_theme.AddThemeFragment.Companion.ADD_STUDENT
import com.summer.itis.curatorapp.ui.work.one_work_list.WorkListFragment
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const
import com.summer.itis.curatorapp.utils.Const.DESC_KEY
import com.summer.itis.curatorapp.utils.Const.MAX_LENGTH
import com.summer.itis.curatorapp.utils.Const.REQUEST_CODE
import com.summer.itis.curatorapp.utils.Const.SEND_THEME
import com.summer.itis.curatorapp.utils.Const.STUDENT_TYPE
import com.summer.itis.curatorapp.utils.Const.TYPE
import com.summer.itis.curatorapp.utils.Const.USER_ID
import com.summer.itis.curatorapp.utils.Const.USER_KEY
import com.summer.itis.curatorapp.utils.Const.gsonConverter
import kotlinx.android.synthetic.main.layout_student.*
import kotlinx.android.synthetic.main.toolbar_add.*

class StudentFragment : BaseFragment<StudentPresenter>(), StudentView, View.OnClickListener {

    lateinit var user: Student
    override lateinit var mainListener: NavigationView

    @InjectPresenter
    lateinit var presenter: StudentPresenter

    private var requestNumber = -1

    companion object {

        const val TAG_CURATOR = "TAG_CURATOR"

        const val EDIT_CURATOR = 1

        fun newInstance(args: Bundle, navigationView: NavigationView): Fragment {
            val fragment = StudentFragment()
            fragment.arguments = args
            fragment.mainListener = navigationView
            return fragment
        }

        fun newInstance(navigationView: NavigationView): Fragment {
            val fragment = StudentFragment()
            fragment.mainListener = navigationView
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_student, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        arguments?.let {
            val userJson = it.getString(USER_KEY)
            if(userJson == null) {
                val id = it.getString(USER_ID)
                presenter.findStudentById(id)
            } else {
                user = gsonConverter.fromJson(it.getString(USER_KEY), Student::class.java)
                requestNumber = it.getInt(REQUEST_CODE)
                initViews()
                setUserData(user)
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private fun initViews() {
        setToolbarData()
        setListeners()
    }

    private fun setToolbarData() {
        mainListener.setToolbar(toolbar_add)
    }

    private fun setListeners() {
        li_skills.setOnClickListener(this)
        li_works.setOnClickListener(this)
        li_desc.setOnClickListener(this)
        btn_add.setOnClickListener(this)
        btn_back.setOnClickListener(this)
    }

    override fun setUserData(student: Student) {
        user = student
        toolbar_add.title = "${user.name} ${user.lastname} ${user.patronymic}"
        tv_username.text = "${user.name} ${user.lastname} ${user.patronymic}"
        tv_group.text = "${user.groupNumber + Const.SPACE + "," + Const.SPACE + user.year + Const.SPACE + context?.getString(R.string.course)}"
        tv_desc.text = AppHelper.cutLongDescription(user.description, MAX_LENGTH)
        AppHelper.setUserPhoto(iv_user_photo, user, activity as Activity)
    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.btn_add -> giveTheme()

            R.id.btn_back -> backFragment()

            R.id.li_skills -> showSkills()

            R.id.li_works -> showWorks()

            R.id.li_desc -> showDesc()
        }
    }

    private fun showDesc() {
        val args = Bundle()
        args.putString(DESC_KEY, user.description)
        args.putString(TYPE, STUDENT_TYPE)
        val fragment = DescriptionFragment.newInstance(args, mainListener)
        mainListener.showFragment(SHOW_THEMES, this, fragment)
//        mainListener.pushFragments(TAB_STUDENTS, fragment, true)
    }

    private fun giveTheme() {

        val args = Intent()
        val studentJson = gsonConverter.toJson(user)
        args.putExtra(USER_KEY, studentJson)
        when (requestNumber) {

            ADD_STUDENT ->  targetFragment?.onActivityResult(AddThemeFragment.ADD_STUDENT, Activity.RESULT_OK, args)

            SEND_THEME -> targetFragment?.onActivityResult(SEND_THEME, Activity.RESULT_OK, args)
        }

        mainListener.hideFragment()
    }

    private fun showSkills() {
        val fragment = SkillListFragment.newInstance(argUser(user, STUDENT_TYPE), mainListener)
        mainListener.showFragment(SHOW_THEMES, this, fragment)
//        mainListener.pushFragments(TAB_STUDENTS, fragment, true)
    }

    private fun showWorks() {
        val fragment = WorkListFragment.newInstance(argUser(user, STUDENT_TYPE), mainListener)
        mainListener.showFragment(SHOW_THEMES, this, fragment)

//        mainListener.pushFragments(TAB_STUDENTS, fragment, true)
    }


}
