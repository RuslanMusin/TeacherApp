package com.summer.itis.curatorapp.ui.theme.edit_theme

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
import com.summer.itis.curatorapp.R.string.subject
import com.summer.itis.curatorapp.model.skill.Skill
import com.summer.itis.curatorapp.model.skill.Subject
import com.summer.itis.curatorapp.model.theme.SuggestionTheme
import com.summer.itis.curatorapp.model.theme.Theme
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
import com.summer.itis.curatorapp.ui.theme.add_theme.AddThemePresenter
import com.summer.itis.curatorapp.ui.theme.add_theme.AddThemeView
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const.ADD_SKILL
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
import kotlinx.android.synthetic.main.fragment_edit_theme.*
import kotlinx.android.synthetic.main.toolbar_back_done.*
import java.util.*

class EditThemeFragment : BaseFragment<EditThemePresenter>(), EditThemeView, View.OnClickListener {

    private lateinit var theme: Theme
    private var suggestionTheme: SuggestionTheme? = null

    private var type = ADD_THEME_TYPE

    override lateinit var mainListener: NavigationView

    private var subject: Subject? = null

    private var studentType = ALL_CHOOSED

    @InjectPresenter
    lateinit var presenter: EditThemePresenter

    private var skills: MutableList<Skill> = ArrayList()
    private var listSkills: MutableList<String> = ArrayList()


    companion object {

        const val ADD_SUBJECT: Int = 1

        fun newInstance(args: Bundle, mainListener: NavigationView): Fragment {
            val fragment = EditThemeFragment()
            fragment.arguments = args
            fragment.mainListener = mainListener
            return fragment
        }

        fun newInstance(mainListener: NavigationView): Fragment {
            val fragment = EditThemeFragment()
            fragment.mainListener = mainListener
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            theme = gsonConverter.fromJson(it.getString(THEME_KEY), Theme::class.java)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_edit_theme, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initViews() {
        setToolbarData()
        setListeners()
        setThemeData()
    }

    private fun setThemeData() {
        et_theme_name.setText(theme.title)
        et_theme_desc.setText(theme.description)

        skills = theme.skills
        subject = theme.subject

        tv_added_subject.text = subject?.name
        tv_added_skills.text = getSkillsText()
    }

    private fun setToolbarData() {
        mainListener.setToolbar(toolbar_back_done)
    }

    private fun setListeners() {
        btn_ok.setOnClickListener(this)
        btn_back.setOnClickListener(this)
        btn_add_subject.setOnClickListener(this)
        btn_add_skill.setOnClickListener(this)
        li_edit_skills.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.btn_ok -> {
                if(validateData()) {
                    theme.title = et_theme_name.text.toString()
                    theme.description = et_theme_desc.text.toString()
                    subject?.let {
                        theme.subjectId = it.id
                        theme.subject = it
                    }
                    theme.skills = skills
                    presenter.saveThemeEdit(theme)
                } else {
                    mainListener.showSnackBar(getString(R.string.invalid_fields))
                }
            }

            R.id.btn_add_subject -> {
                val fragment = AddSubjectFragment.newInstance(mainListener)
                fragment.setTargetFragment(this, ADD_SUBJECT)
                mainListener.showFragment(SHOW_THEMES, this, fragment)
            }

            R.id.btn_back -> backFragment()

            R.id.btn_add_skill -> addSkill()

            R.id.li_edit_skills -> editSkills()
        }
    }

    private fun addSkill() {
        val fragment = ChooseSkillFragment.newInstance(mainListener)
        fragment.setTargetFragment(this, ADD_SKILL)
        mainListener.showFragment(SHOW_THEMES, this, fragment)
    }


    private fun editSkills() {
        if(!tv_added_skills.text.equals(getString(R.string.doesnt_matter_for_all))) {
            val args = Bundle()
            val listJson = gsonConverter.toJson(skills)
            args.putString(SKILL_KEY, listJson)
            val fragment = EditChooseLIstFragment.newInstance(args, mainListener)
            fragment.setTargetFragment(this, EDIT_CHOOSED_SKILLS)
            mainListener.showFragment(SHOW_THEMES, this, fragment)
        }
    }

    override fun returnEditResult(intent: Intent?) {
        targetFragment?.onActivityResult(EDIT_SUGGESTION, Activity.RESULT_OK, intent)
        mainListener.hideFragment()
    }
    override fun onActivityResult(reqCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(reqCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK) {

            when(reqCode) {

                ADD_SUBJECT -> {
                    Log.d(TAG_LOG, "getResult")
                    data?.getStringExtra(SUBJECT_KEY)?.let {
                        subject = gsonConverter.fromJson(it, Subject::class.java)
                        tv_added_subject.text = subject?.name
                    }
                }

                ADD_SKILL -> {
                    data?.let {
                        val skillJson = it.getStringExtra(SKILL_KEY)
                        val skill = gsonConverter.fromJson(skillJson, Skill::class.java)
                        skills.add(skill)
                        val skillText = "${skill.name} ${getString(R.string.level)} ${skill.level}"
                        listSkills.add(skillText)
                        tv_added_skills.text = getListString(listSkills)
                    }
                }

                EDIT_CHOOSED_SKILLS -> {
                    data?.let {
                        val founderListType = object : TypeToken<ArrayList<Skill>>() { }.type
                        val skillsJson = it.getStringExtra(SKILL_KEY)
                        skills = gsonConverter.fromJson(skillsJson, founderListType)
                        listSkills.clear()
                        if(skills.size == 0) {
                            tv_added_skills.text = getString(R.string.doesnt_matter_for_all)
                        } else {
                            for (i in skills.indices) {
                                listSkills.add("${skills[i].name} ${getString(R.string.level)} ${skills[i].level}")
                            }
                            tv_added_skills.text = getListString(listSkills)
                        }
                    }
                }
            }
        }
    }

    private fun validateData(): Boolean{
        val name = et_theme_name.text.toString()
        val desc  = et_theme_desc.text.toString()
        val subjectName = tv_added_subject.text
        if(name.equals("") || desc.equals("") || subjectName.equals("")) {
            return false
        } else {
            return true
        }
    }

    private fun getListString(list: List<String>): String {
        var listString: String = ""
        for((i,item) in list.withIndex()) {
            listString += item
            if(i != list.lastIndex) {
                listString += " , "
            }

        }
        listString.removeSuffix(",")
        return listString
    }

    private fun getSkillsText(): String {
        if(skills.size != 0) {
            listSkills.clear()
            for (i in skills.indices) {
                listSkills.add("${skills[i].name} ${getString(R.string.level)} ${skills[i].level}")
            }
            return getListString(listSkills)
        } else {
            return getString(R.string.doesnt_matter_for_all)
        }
    }
}
