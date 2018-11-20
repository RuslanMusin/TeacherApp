package com.summer.itis.curatorapp.ui.theme.theme_item

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.arellomobile.mvp.presenter.InjectPresenter
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.R.string.level
import com.summer.itis.curatorapp.model.skill.Skill
import com.summer.itis.curatorapp.model.theme.Theme
import com.summer.itis.curatorapp.model.user.Student
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragment
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationBaseActivity.Companion.SHOW_THEMES
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationBaseActivity.Companion.TAB_THEMES
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationView
import com.summer.itis.curatorapp.ui.curator.curator_item.description.view.DescriptionFragment
import com.summer.itis.curatorapp.ui.student.student_item.StudentFragment
import com.summer.itis.curatorapp.ui.student.student_list.StudentListFragment
import com.summer.itis.curatorapp.ui.theme.add_theme.AddThemeFragment
import com.summer.itis.curatorapp.ui.theme.edit_theme.EditThemeFragment
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const.DESC_KEY
import com.summer.itis.curatorapp.utils.Const.EDIT_SUGGESTION
import com.summer.itis.curatorapp.utils.Const.ID_KEY
import com.summer.itis.curatorapp.utils.Const.REQUEST_CODE
import com.summer.itis.curatorapp.utils.Const.SEND_THEME
import com.summer.itis.curatorapp.utils.Const.TAB_NAME
import com.summer.itis.curatorapp.utils.Const.THEME_KEY
import com.summer.itis.curatorapp.utils.Const.THEME_TYPE
import com.summer.itis.curatorapp.utils.Const.TYPE
import com.summer.itis.curatorapp.utils.Const.USER_ID
import com.summer.itis.curatorapp.utils.Const.USER_KEY
import com.summer.itis.curatorapp.utils.Const.gsonConverter
import com.summer.itis.curatorapp.utils.SkillViewHelper
import kotlinx.android.synthetic.main.layout_expandable.*
import kotlinx.android.synthetic.main.layout_expandable_text_view.*
import kotlinx.android.synthetic.main.layout_item_skill.*
import kotlinx.android.synthetic.main.layout_theme.*
import kotlinx.android.synthetic.main.toolbar_back_add_edit.*
import java.util.*

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
        super.onViewCreated(view, savedInstanceState)
        initViews()
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
        mainListener.setToolbar(toolbar_back_add_edit)
        mainListener.setToolbarTitle(theme.title)
    }

    private fun setListeners() {
        if(theme.dateAcceptance?.time != null) {
            btn_edit.visibility = View.GONE
            btn_add.visibility = View.GONE
        } else {
            btn_edit.setOnClickListener(this)
            btn_add.setOnClickListener(this)
        }
        btn_back.setOnClickListener(this)
        li_skills.setOnClickListener(this)
    }

    private fun setData() {
        if(theme.skills.size == 0) {
            li_skills.visibility = View.GONE
        } else {
            for(skill in theme.skills) {
                addSkillView(skill)
            }

//            tv_content.text = this.activity?.let { SkillViewHelper.getSkillsText(theme.skills, it) }
        }
        tv_title.text = theme.title
        tv_curator.text = theme.curator?.getFullName()
        val name = theme.student?.getFullName()
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

            R.id.btn_add -> sendToStudent()

            R.id.li_student -> showStudent()

            R.id.li_desc -> showDesc()

            R.id.li_skills -> {
                expandable_layout.toggle()
                val divider = li_skills.findViewById<View>(R.id.divider)
                if(expandable_layout.isExpanded) {
                    divider.visibility = View.GONE
                } else {
                    divider.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun addSkillView(skill: Skill) {
        val view: View = layoutInflater.inflate(R.layout.layout_item_skill, li_added_skills,false)
        val tvAddedSkill: TextView = view.findViewById(R.id.tv_added_skill_name)
        val tvAddedLevel: TextView = view.findViewById(R.id.tv_added_skill_level)

        tvAddedSkill.text = skill.name
        tvAddedLevel.text = getString(R.string.skill_level, skill.level)

        li_added_skills.addView(view)
    }

    private fun sendToStudent() {
        this.activity?.let {
            MaterialDialog.Builder(it)
                    .title(R.string.should_send_theme)
                    .content(R.string.send_theme_content)
                    .positiveText(R.string.button_ok)
                    .negativeText(R.string.cancel)
                    .onNegative { dialog, which ->
                        dialog.dismiss()
                    }
                    .onPositive { dialog, which ->
                        val args = Bundle()
                        args.putInt(REQUEST_CODE, SEND_THEME)
                        val fragment = StudentListFragment.newInstance(args, mainListener)
                        fragment.setTargetFragment(this, SEND_THEME)
                        mainListener.showFragment(SHOW_THEMES,this, fragment)
                    }
                    .show()
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
        this.activity?.let {
            MaterialDialog.Builder(it)
                    .title(R.string.should_edit_theme)
                    .content(R.string.theme_suggestions_will_be_changed)
                    .positiveText(R.string.agree)
                    .negativeText(R.string.cancel)
                    .onNegative { dialog, which ->
                        dialog.dismiss()
                    }
                    .onPositive { dialog, which ->
                        val args = Bundle()
                        args.putString(THEME_KEY, gsonConverter.toJson(theme))
                        args.putString(TYPE, THEME_TYPE)
                        val fragment = EditThemeFragment.newInstance(args, mainListener)
                        fragment.setTargetFragment(this, EDIT_SUGGESTION)
                        mainListener.showFragment(SHOW_THEMES,this, fragment)
                    }
                    .show()
        }

    }

    private fun showStudent() {
        val args = Bundle()
        args.putString(USER_ID, theme.studentId)
        args.putString(TAB_NAME, TAB_THEMES)
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
                        theme = gsonConverter.fromJson(json, Theme::class.java)
                        tv_subject.text = theme.subject.name
                        tv_title.text = theme.title
                        expand_text_view.text = theme.description

                        if(theme.skills.size != 0) {
                            li_skills.visibility = View.VISIBLE
                            expandable_layout.collapse()
                            li_added_skills.removeAllViewsInLayout()
                            for(skill in theme.skills) {
                                addSkillView(skill)
                            }
                        } else {
                            li_skills.visibility = View.GONE
                        }

                        mainListener.showSnackBar(getString(R.string.changes_updated))

                    }
                }

                SEND_THEME -> {
                    val studentJson = data?.getStringExtra(USER_KEY)
                    studentJson?.let {
                        val student = gsonConverter.fromJson(studentJson, Student::class.java)
                        context?.let { it1 -> presenter.sendSuggestion(theme, student, it1) }

                        mainListener.showSnackBar(getString(R.string.suggestion_was_sended))
                    }
                }
            }
        }
    }
}
