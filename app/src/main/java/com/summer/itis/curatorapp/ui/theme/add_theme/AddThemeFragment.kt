package com.summer.itis.curatorapp.ui.theme.add_theme

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.annotation.ArrayRes
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.gson.reflect.TypeToken
import com.summer.itis.curatorapp.R
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
import com.summer.itis.curatorapp.utils.Const.TYPE
import com.summer.itis.curatorapp.utils.Const.USER_KEY
import com.summer.itis.curatorapp.utils.Const.gsonConverter
import kotlinx.android.synthetic.main.fragment_add_theme.*
import kotlinx.android.synthetic.main.toolbar_back_done.*
import java.util.*
import kotlin.collections.ArrayList

class AddThemeFragment : BaseFragment<AddThemePresenter>(), AddThemeView, View.OnClickListener {

    private lateinit var theme: Theme
    private var suggestionTheme: SuggestionTheme? = null

    private var type = ADD_THEME_TYPE

    override lateinit var mainListener: NavigationView

    private var studentId: String? = null
    private var subject: Subject? = null
    private var student: Student? = null

    private var studentType = ALL_CHOOSED

    @InjectPresenter
    lateinit var presenter: AddThemePresenter

    private var skills: MutableList<Skill> = ArrayList()
    private var listSkills: MutableList<String> = ArrayList()

    private var imageViews: MutableList<ImageView> = ArrayList()
    private var liViews: MutableList<LinearLayout> = ArrayList()

    private lateinit var checkListener: View.OnClickListener

    companion object {

        const val ADD_SUBJECT: Int = 1
        const val ADD_STUDENT: Int = 2

        fun newInstance(args: Bundle, mainListener: NavigationView): Fragment {
            val fragment = AddThemeFragment()
            fragment.arguments = args
            fragment.mainListener = mainListener
            return fragment
        }

        fun newInstance(mainListener: NavigationView): Fragment {
            val fragment = AddThemeFragment()
            fragment.mainListener = mainListener
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        mainListener.hideBottomNavigation()
        val view = inflater.inflate(R.layout.fragment_add_theme, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        val themeJson = arguments?.getString(THEME_KEY)
        if(themeJson != null) {
            arguments?.let {
                type = it.getString(TYPE)
                if(type.equals(SUGGESTION_TYPE)) {
                    suggestionTheme = gsonConverter.fromJson(themeJson, SuggestionTheme::class.java)
                    theme = suggestionTheme?.theme!!
                    setProgressData()
                } else {
                    theme = gsonConverter.fromJson(themeJson, Theme::class.java)
                    setStaticData()
                }
//                btn_create_questions.setText(getString(R.string.save_changes))
                mainListener.setToolbarTitle(getString(R.string.edit))
            }
        } else {
            theme = Theme()
        }
        arguments?.getString(ID_KEY)?.let { studentId = it }
        super.onViewCreated(view, savedInstanceState)
    }


    private fun setProgressData() {
        tv_added_subject.text = suggestionTheme?.themeProgress?.subject?.name
        et_theme_name.setText(suggestionTheme?.themeProgress?.title)
        et_theme_desc.setText(suggestionTheme?.themeProgress?.description)
    }

    private fun setStaticData() {
        tv_added_subject.text = theme?.subject?.name
        et_theme_name.setText(theme.title)
        et_theme_desc.setText(theme.description)
    }

    private fun initViews() {
        setToolbarData()
        setListeners()


     /*   spinner_student.setItems(getString(R.string.all_students_choosed))
        spinner_student.setOnItemSelectedListener { view, position, id, item ->
            if(!item.equals(getString(R.string.all_students_choosed))) {
                studentType = ONE_CHOOSED
            } else {
                studentType = ALL_CHOOSED
            }
        }*/
    }

    private fun setToolbarData() {
        mainListener.setToolbar(toolbar_back_done)
    }

    private fun setListeners() {
       /* btn_create_questions.setOnClickListener(this)
        btn_create_questions.visibility = View.GONE*/

        btn_ok.setOnClickListener(this)
        tv_add_student.setOnClickListener(this)
        iv_remove_student.setOnClickListener(this)
        tv_add_subject.setOnClickListener(this)
        btn_back.setOnClickListener(this)

        tv_add_skill.setOnClickListener(this)
        li_added_skills.setOnClickListener(this)

        checkListener = object: View.OnClickListener{
            override fun onClick(v: View?) {
                val ivRemove = v as ImageView
                val index = imageViews.indexOf(ivRemove)
                val liSkill = liViews[index]
                li_added_skills.removeView(liSkill)
                liViews.removeAt(index)
                imageViews.removeAt(index)
                skills.removeAt(index)
//                textViews.removeAt(index)

                if(liViews.size == 0) {
                    tv_added_skills.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.btn_ok -> {
                if(validateData()) {
                    theme.id = "${Random().nextInt(24000)}"
                    theme.title = et_theme_name.text.toString()
                    theme.description = et_theme_desc.text.toString()
                    theme.curatorId = AppHelper.currentCurator.id
                    studentId?.let { theme.studentId = it }
                    theme.curator = AppHelper.currentCurator
                    theme.student = student
                    subject?.let {
                        theme.subjectId = it.id
                        theme.subject = it
                    }
                    theme.dateCreation = Date()
                    theme.skills = skills
                    context?.let { presenter.addTheme(theme, it) }
//                    backFragment()
                } else {
                    mainListener.showSnackBar(getString(R.string.invalid_fields))
                }
            }

            R.id.tv_add_subject -> {
                val fragment = AddSubjectFragment.newInstance(mainListener)
                fragment.setTargetFragment(this, ADD_SUBJECT)
                mainListener.showFragment(SHOW_THEMES, this, fragment)
//                mainListener.pushFragments(TAB_STUDENTS, fragment, false)
//                mainListener.loadFragment(fragment)

            }

            R.id.tv_add_student -> {
                val fragment = StudentListFragment.newInstance(mainListener)
                fragment.setTargetFragment(this, ADD_SUBJECT)
                mainListener.showFragment(SHOW_THEMES, this, fragment)
            }

            R.id.iv_remove_student -> {
                studentType = ALL_CHOOSED
                tv_added_students.text = getString(R.string.all_students_choosed)
                iv_remove_student.visibility = View.GONE

            }

            R.id.btn_back -> backFragment()

            R.id.tv_add_skill -> addSkill()

//            R.id.li_added_skills -> editSkills()
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

    override fun getResultAfterEdit(isEdit: Boolean, intent: Intent?) {
        if(isEdit) {
            targetFragment?.onActivityResult(EDIT_SUGGESTION, Activity.RESULT_OK, intent)
            mainListener.hideFragment()
        } else {
            backFragment()
        }
    }
    override fun onActivityResult(reqCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(reqCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK) {

            when(reqCode) {

                ADD_SUBJECT -> {
                    Log.d(TAG_LOG, "getResult")
                    data?.getStringExtra(SUBJECT_KEY)?.let {
                        subject = gsonConverter.fromJson(it, Subject::class.java)
                        li_added_subject.visibility = View.VISIBLE
                        tv_added_subject.text = subject?.name
                        tv_add_subject.text = getString(R.string.change_subject)
                    }
                }

                ADD_STUDENT -> {
                    data?.getStringExtra(USER_KEY)?.let {
                        student = gsonConverter.fromJson(it, Student::class.java)
                        studentType = ONE_CHOOSED
//                        tv_added_student.text = student?.getFullName()
                       /* spinner_student.setItems(getString(R.string.all_students_choosed), student?.getFullName())
                        spinner_student.selectedIndex = 1*/
                        tv_added_students.text =  student?.getFullName()
                        iv_remove_student.visibility = View.VISIBLE
                    }
                }

                ADD_SKILL -> {
                    data?.let {
                        val skillJson = it.getStringExtra(SKILL_KEY)
                        val skill = gsonConverter.fromJson(skillJson, Skill::class.java)
                        skills.add(skill)
//                        val skillText = "${skill.name}
                        if(liViews.size == 0) {
                            tv_added_skills.visibility = View.GONE
                        }
                        addSkillView(skill.name, skill.level)
                     /*   listSkills.add(skillText)
                        tv_added_skills.text = getListString(listSkills)*/
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

    private fun addSkillView(skill: String, level: String) {
        val view: View = layoutInflater.inflate(R.layout.layout_item_tv_with_clear, li_added_skills,false)
        val ivRemoveSkill: ImageView = view.findViewById(R.id.iv_remove_skill)
        val tvAddedSkill: TextView = view.findViewById(R.id.tv_added_skill_name)
        val tvAddedLevel: TextView = view.findViewById(R.id.tv_added_skill_level)

        ivRemoveSkill.setOnClickListener(checkListener)
        tvAddedSkill.text = skill
        tvAddedLevel.text = getString(R.string.skill_level, level)
        imageViews.add(ivRemoveSkill)
        liViews.add(view as LinearLayout)

        li_added_skills.addView(view)
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
}
