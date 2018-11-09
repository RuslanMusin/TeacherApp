package com.summer.itis.curatorapp.ui.student.search

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.afollestad.materialdialogs.MaterialDialog
import com.arellomobile.mvp.presenter.InjectPresenter
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.user.Student
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragment
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationBaseActivity.Companion.SHOW_THEMES
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationView
import com.summer.itis.curatorapp.ui.student.student_item.StudentFragment
import com.summer.itis.curatorapp.ui.student.student_list.StudentAdapter
import com.summer.itis.curatorapp.ui.student.student_list.StudentListFragment
import com.summer.itis.curatorapp.ui.student.student_list.StudentListPresenter
import com.summer.itis.curatorapp.ui.student.student_list.StudentListView
import com.summer.itis.curatorapp.ui.subject.add_subject.AddSubjectFragment
import com.summer.itis.curatorapp.ui.theme.add_theme.AddThemeFragment
import com.summer.itis.curatorapp.ui.theme.add_theme.AddThemeFragment.Companion.ADD_STUDENT
import com.summer.itis.curatorapp.ui.theme.add_theme.AddThemeFragment.Companion.ADD_SUBJECT
import com.summer.itis.curatorapp.utils.Const.STUDENT_TYPE
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_search_filter.*
import kotlinx.android.synthetic.main.toolbar_add_done.*

class SearchFilterFragment : BaseFragment<StudentListPresenter>(), View.OnClickListener {

    lateinit override var mainListener: NavigationView
    private lateinit var adapter: StudentAdapter

    lateinit var students: MutableList<Student>

    @InjectPresenter
    lateinit var presenter: StudentListPresenter

    companion object {

        const val TAG_SKILLS = "TAG_SKILLS"

        const val ADD_SKILL = 1

        const val EDIT_CHOOSED_SKILLS = 2

        const val ADD_COURSE = 3


        fun newInstance(args: Bundle, navigationView: NavigationView): Fragment {
            val fragment = StudentListFragment()
            fragment.arguments = args
            fragment.mainListener = navigationView
            return fragment
        }

        fun newInstance(navigationView: NavigationView): Fragment {
            val fragment = StudentListFragment()
            fragment.mainListener = navigationView
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search_filter, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        setToolbarData()
        setListeners()
    }

    private fun setToolbarData() {
        mainListener.setToolbar(toolbar_add_done)
        btn_add.visibility = View.GONE
    }

    private fun setListeners() {
        btn_back.setOnClickListener(this)
        btn_add_skill.setOnClickListener(this)
        li_choose_course.setOnClickListener(this)
        li_edit_skills.setOnClickListener(this)
    }

    override fun onClick(v: View) {

        when(v.id) {

            R.id.btn_back -> backFragment()

            R.id.btn_add_skill -> addSkill()

            R.id.li_choose_course -> chooseCourse()

            R.id.li_edit_skills -> editSkills()
        }
    }

    private fun addSkill() {
        val fragment = AddSubjectFragment.newInstance(mainListener)
        fragment.setTargetFragment(this, ADD_SUBJECT)
        mainListener.showFragment(SHOW_THEMES, this, fragment)
    }

    private fun chooseCourse() {
      /*  this.activity?.let {
            MaterialDialog.Builder(it)
                    .title("Выберите курс")
                    .positiveText("ОК")
                    .negativeText("Отмена")

        }
        MaterialDialog(this)

        .listItemsMultiChoice(R.array.my_items, initialSelection = indices)
                .show()*/
    }

    private fun editSkills() {

    }

    override fun onActivityResult(reqCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(reqCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK) {

            when(reqCode) {

                ADD_STUDENT -> {
                    targetFragment?.onActivityResult(ADD_STUDENT, Activity.RESULT_OK, data)
                    mainListener.hideFragment()
                }
            }
        }
    }
}