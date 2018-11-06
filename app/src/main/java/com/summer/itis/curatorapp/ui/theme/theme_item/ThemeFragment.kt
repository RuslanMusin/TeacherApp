package com.summer.itis.curatorapp.ui.theme.theme_item

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.R.drawable.item
import com.summer.itis.curatorapp.R.id.*
import com.summer.itis.curatorapp.model.theme.Theme
import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragment
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationBaseActivity.Companion.TAB_PROFILE
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationBaseActivity.Companion.TAB_STUDENTS
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationBaseActivity.Companion.TAB_THEMES
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationView
import com.summer.itis.curatorapp.ui.curator.curator_item.description.view.DescriptionFragment
import com.summer.itis.curatorapp.ui.curator.curator_item.edit.EditCuratorFragment
import com.summer.itis.curatorapp.ui.curator.curator_item.view.CuratorFragment
import com.summer.itis.curatorapp.ui.curator.curator_item.view.CuratorPresenter
import com.summer.itis.curatorapp.ui.curator.curator_item.view.CuratorView
import com.summer.itis.curatorapp.ui.login.LoginActivity
import com.summer.itis.curatorapp.ui.skill.skill_list.view.SkillListFragment
import com.summer.itis.curatorapp.ui.student.student_item.StudentFragment
import com.summer.itis.curatorapp.ui.theme.add_theme.AddThemeFragment
import com.summer.itis.curatorapp.ui.work.one_work_list.WorkListFragment
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const.CURATOR_TYPE
import com.summer.itis.curatorapp.utils.Const.DESC_KEY
import com.summer.itis.curatorapp.utils.Const.EDIT_SUGGESTION
import com.summer.itis.curatorapp.utils.Const.EDIT_THEME
import com.summer.itis.curatorapp.utils.Const.ID_KEY
import com.summer.itis.curatorapp.utils.Const.MAX_LENGTH
import com.summer.itis.curatorapp.utils.Const.OWNER_TYPE
import com.summer.itis.curatorapp.utils.Const.STUDENT_TYPE
import com.summer.itis.curatorapp.utils.Const.SUGGESTION_TYPE
import com.summer.itis.curatorapp.utils.Const.THEME_KEY
import com.summer.itis.curatorapp.utils.Const.THEME_TYPE
import com.summer.itis.curatorapp.utils.Const.TYPE
import com.summer.itis.curatorapp.utils.Const.USER_ID
import com.summer.itis.curatorapp.utils.Const.USER_KEY
import com.summer.itis.curatorapp.utils.Const.gsonConverter
import kotlinx.android.synthetic.main.layout_expandable_text_view.*
import kotlinx.android.synthetic.main.layout_theme.*
import kotlinx.android.synthetic.main.toolbar_edit.*

class ThemeFragment : BaseFragment<ThemePresenter>(), ThemeView, View.OnClickListener {

    override lateinit var mainListener: NavigationView
    lateinit var theme: Theme

    @InjectPresenter
    lateinit var presenter: ThemePresenter

    companion object {

        const val TAG_CURATOR = "TAG_CURATOR"

        const val EDIT_CURATOR = 1

        fun newInstance(args: Bundle, navigationView: NavigationView): Fragment {
            val fragment = ThemeFragment()
            fragment.arguments = args
            fragment.mainListener = navigationView
            return fragment
        }

        fun newInstance(navigationView: NavigationView): Fragment {
            val fragment = ThemeFragment()
            fragment.mainListener = navigationView
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_theme, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        super.onViewCreated(view, savedInstanceState)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            theme = gsonConverter.fromJson(it.getString(THEME_KEY), Theme::class.java)
        }
    }

    fun initViews() {
        setToolbarData()
        setListeners()
        setData()
    }

    private fun setToolbarData() {
        mainListener.setToolbar(toolbar_edit)
        mainListener.setToolbarTitle(theme.title)
        btn_back.visibility = View.VISIBLE

    }

    private fun setListeners() {
        btn_edit.setOnClickListener(this)
        btn_back.setOnClickListener(this)
    }

    private fun setData() {
        tv_title.text = theme.title
        tv_curator.text = theme.curator?.printFullName()
        val name = theme.student?.printFullName()
        if(name == null) {
            tv_student.text = getString(R.string.theme_not_choosed)
        } else {
            tv_student.text = name
        }
        tv_subject.text = theme.subject.name
        expand_text_view.text = theme.description
    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.btn_back -> backFragment()

            R.id.btn_edit -> editProfile()

            R.id.li_student -> showStudent()

            R.id.li_desc -> showDesc()
        }
    }

    private fun showDesc() {
        val args = Bundle()
        args.putString(DESC_KEY, theme.description)
        args.putString(TYPE, THEME_TYPE)
        args.putString(USER_ID, AppHelper.currentCurator.id)
        args.putString(ID_KEY, theme.id)
        val fragment = DescriptionFragment.newInstance(args, mainListener)
        mainListener.pushFragments(TAB_THEMES, fragment, true)
    }

    private fun editProfile() {
        val args = Bundle()
        args.putString(THEME_KEY, gsonConverter.toJson(theme))
        args.putString(TYPE, THEME_TYPE)
        val fragment = AddThemeFragment.newInstance(args, mainListener)
        fragment.setTargetFragment(this, EDIT_SUGGESTION)
        mainListener.showFragment(this, fragment)
    }

    private fun showStudent() {
        val args = Bundle()
        args.putString(USER_ID, theme.studentId)
        val fragment = StudentFragment.newInstance(args, mainListener)
        mainListener.pushFragments(TAB_THEMES, fragment, true)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when(requestCode) {

                EDIT_SUGGESTION -> {
                    val json = data?.getStringExtra(THEME_KEY)
                    json?.let {
                        val themeProgress = gsonConverter.fromJson(json, Theme::class.java)
                        tv_subject.text = themeProgress.subject.name
                        tv_title.text = themeProgress.title
                        expand_text_view.text = themeProgress.description
                    }
                }
            }
        }
    }
}
