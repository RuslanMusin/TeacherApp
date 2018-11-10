package com.summer.itis.curatorapp.ui.theme.edit_suggestion

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.gson.reflect.TypeToken
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.R.drawable.student
import com.summer.itis.curatorapp.R.id.*
import com.summer.itis.curatorapp.R.string.skills
import com.summer.itis.curatorapp.R.string.subject
import com.summer.itis.curatorapp.model.skill.Skill
import com.summer.itis.curatorapp.model.theme.SuggestionTheme
import com.summer.itis.curatorapp.model.theme.ThemeProgress
import com.summer.itis.curatorapp.model.user.Student
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragment
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationBaseActivity.Companion.SHOW_THEMES
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationView
import com.summer.itis.curatorapp.ui.student.search.choose_skill_main.ChooseSkillFragment
import com.summer.itis.curatorapp.ui.student.search.edit_choose_skill.EditChooseLIstFragment
import com.summer.itis.curatorapp.ui.student.search.search_filter.SearchFilterFragment.Companion.EDIT_CHOOSED_SKILLS
import com.summer.itis.curatorapp.ui.student.student_list.StudentListFragment
import com.summer.itis.curatorapp.ui.subject.add_subject.AddSubjectFragment
import com.summer.itis.curatorapp.ui.theme.add_theme.AddThemeFragment
import com.summer.itis.curatorapp.ui.theme.add_theme.AddThemeFragment.Companion.ADD_STUDENT
import com.summer.itis.curatorapp.ui.theme.add_theme.AddThemeFragment.Companion.ADD_SUBJECT
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const.ADD_THEME_TYPE
import com.summer.itis.curatorapp.utils.Const.ALL_CHOOSED
import com.summer.itis.curatorapp.utils.Const.EDIT_SUGGESTION
import com.summer.itis.curatorapp.utils.Const.ID_KEY
import com.summer.itis.curatorapp.utils.Const.ONE_CHOOSED
import com.summer.itis.curatorapp.utils.Const.SKILL_KEY
import com.summer.itis.curatorapp.utils.Const.SUBJECT_KEY
import com.summer.itis.curatorapp.utils.Const.SUGGESTION_TYPE
import com.summer.itis.curatorapp.utils.Const.TAG_LOG
import com.summer.itis.curatorapp.utils.Const.THEME_KEY
import com.summer.itis.curatorapp.utils.Const.USER_KEY
import com.summer.itis.curatorapp.utils.Const.gsonConverter
import com.summer.itis.curatorapp.utils.SkillViewHelper.Companion.getListString
import kotlinx.android.synthetic.main.fragment_edit_suggestion.*
import kotlinx.android.synthetic.main.toolbar_back_done.*
import java.util.*

class EditSuggestionFragment : BaseFragment<EditSuggestionPresenter>(), EditSuggestionView, View.OnClickListener {

    private lateinit var themeProgress: ThemeProgress

    override lateinit var mainListener: NavigationView

    private var studentType = ALL_CHOOSED

    @InjectPresenter
    lateinit var presenter: EditSuggestionPresenter

    companion object {

        fun newInstance(args: Bundle, mainListener: NavigationView): Fragment {
            val fragment = EditSuggestionFragment()
            fragment.arguments = args
            fragment.mainListener = mainListener
            return fragment
        }

        fun newInstance(mainListener: NavigationView): Fragment {
            val fragment = EditSuggestionFragment()
            fragment.mainListener = mainListener
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            themeProgress = gsonConverter.fromJson(it.getString(THEME_KEY), ThemeProgress::class.java)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_edit_suggestion, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        setToolbarData()
        setListeners()
        setThemeData()
    }

    private fun setThemeData() {
        et_theme_name.setText(themeProgress.title)
        et_theme_desc.setText(themeProgress.description)
    }

    private fun setToolbarData() {
        mainListener.setToolbar(toolbar_back_done)
        mainListener.setToolbarTitle(getString(R.string.change_suggestion))
    }

    private fun setListeners() {
        btn_ok.setOnClickListener(this)
        btn_back.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.btn_ok -> {
                if(validateData()) {
                    val title = et_theme_name.text.toString()
                    val desc = et_theme_desc.text.toString()

                    themeProgress.title = title
                    themeProgress.description = desc

                    presenter.saveSuggestionEdit(themeProgress)
                }
            }

            R.id.btn_back -> backFragment()

        }
    }
    override fun returnEditResult(intent: Intent?) {
        targetFragment?.onActivityResult(EDIT_SUGGESTION, Activity.RESULT_OK, intent)
        mainListener.hideFragment()
    }

    private fun validateData(): Boolean{
        val name = et_theme_name.text.toString()
        val desc  = et_theme_desc.text.toString()
        if(name.equals("") || desc.equals("")) {
            return false
        } else {
            return true
        }
    }
}