package com.summer.itis.curatorapp.ui.work.work_item

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afollestad.materialdialogs.MaterialDialog
import com.arellomobile.mvp.presenter.InjectPresenter
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.user.Student
import com.summer.itis.curatorapp.model.work.Work
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragment
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationBaseActivity.Companion.SHOW_THEMES
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationBaseActivity.Companion.TAB_THEMES
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationBaseActivity.Companion.TAB_WORKS
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationView
import com.summer.itis.curatorapp.ui.curator.curator_item.description.view.DescriptionFragment
import com.summer.itis.curatorapp.ui.student.student_item.StudentFragment
import com.summer.itis.curatorapp.utils.Const.TAB_NAME
import com.summer.itis.curatorapp.utils.Const.THEME_TYPE
import com.summer.itis.curatorapp.utils.Const.USER_ID
import com.summer.itis.curatorapp.utils.Const.USER_KEY
import com.summer.itis.curatorapp.utils.Const.WORK_KEY
import com.summer.itis.curatorapp.utils.Const.gsonConverter
import com.summer.itis.curatorapp.utils.SkillViewHelper
import kotlinx.android.synthetic.main.fragment_work.*
import kotlinx.android.synthetic.main.layout_expandable_text_view.*
import kotlinx.android.synthetic.main.toolbar_back.*

class WorkFragment: BaseFragment<WorkPresenter>(), WorkView, View.OnClickListener {

    override lateinit var mainListener: NavigationView
    lateinit var work: Work

    @InjectPresenter
    lateinit var presenter: WorkPresenter

    companion object {

        const val TAG_CURATOR = "TAG_CURATOR"

        const val EDIT_CURATOR = 1

        fun newInstance(args: Bundle, navigationView: NavigationView): Fragment {
            val fragment = WorkFragment()
            fragment.arguments = args
            fragment.mainListener = navigationView
            return fragment
        }

        fun newInstance(navigationView: NavigationView): Fragment {
            val fragment = WorkFragment()
            fragment.mainListener = navigationView
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_work, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            work = gsonConverter.fromJson(it.getString(WORK_KEY), Work::class.java)
        }
    }

    fun initViews() {
        setToolbarData()
        setListeners()
        setData()
    }

    private fun setToolbarData() {
        mainListener.setToolbar(toolbar_back)
        mainListener.setToolbarTitle(getString(R.string.work))
    }

    private fun setListeners() {
        btn_back.setOnClickListener(this)
    }

    private fun setData() {
        tv_title.text = work.theme.title
        tv_curator.text = work.theme.curator?.getFullName()
        val name = work.theme.student?.getFullName()
        if(name == null) {
            tv_student.text = getString(R.string.theme_not_choosed)
        } else {
            tv_student.text = name
        }
        tv_subject.text = work.theme.subject.name
        expand_text_view.text = work.theme.description
    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.btn_back -> backFragment()

            R.id.li_student -> showStudent()

//            R.id.li_desc -> showDesc()
        }
    }

    private fun showStudent() {
        val args = Bundle()
        args.putString(USER_ID, work.theme.student?.id)
        args.putString(TAB_NAME, TAB_WORKS)
        val fragment = StudentFragment.newInstance(args, mainListener)
        mainListener.pushFragments(TAB_WORKS, fragment, true)
    }
}
