package com.summer.itis.curatorapp.ui.student.search.search_filter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.afollestad.materialdialogs.MaterialDialog
import com.arellomobile.mvp.presenter.InjectPresenter
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.user.Student
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragment
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationBaseActivity.Companion.SHOW_THEMES
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationView
import com.summer.itis.curatorapp.ui.student.student_list.StudentAdapter
import com.summer.itis.curatorapp.ui.student.student_list.StudentListPresenter
import kotlinx.android.synthetic.main.fragment_search_filter.*
import kotlinx.android.synthetic.main.toolbar_add_done.*
import com.google.gson.reflect.TypeToken
import com.summer.itis.curatorapp.model.skill.Skill
import com.summer.itis.curatorapp.ui.student.search.choose_skill_main.ChooseSkillFragment
import com.summer.itis.curatorapp.ui.student.search.edit_choose_skill.EditChooseLIstFragment
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const.ADD_SKILL
import com.summer.itis.curatorapp.utils.Const.COURSE_KEY
import com.summer.itis.curatorapp.utils.Const.FILTERS
import com.summer.itis.curatorapp.utils.Const.SKILL_KEY
import com.summer.itis.curatorapp.utils.Const.gsonConverter
import java.util.*


class SearchFilterFragment : BaseFragment<SearchFilterPresenter>(), SearchFilterView, View.OnClickListener {

    lateinit override var mainListener: NavigationView
    private lateinit var adapter: StudentAdapter

    lateinit var students: MutableList<Student>

    @InjectPresenter
    lateinit var presenter: SearchFilterPresenter

    private var listCourses: MutableList<String> = ArrayList()
    private var listSkills: MutableList<String> = ArrayList()
    private var skills: MutableList<Skill> = ArrayList()
    private var courses: MutableList<Long> = ArrayList()

    private var selectedYears: MutableList<Int> = ArrayList()


    companion object {

        const val TAG_SKILLS = "TAG_SKILLS"

        const val EDIT_CHOOSED_SKILLS = 5

        const val ADD_COURSE = 3


        fun newInstance(args: Bundle, navigationView: NavigationView): Fragment {
            val fragment = SearchFilterFragment()
            fragment.arguments = args
            fragment.mainListener = navigationView
            return fragment
        }

        fun newInstance(navigationView: NavigationView): Fragment {
            val fragment = SearchFilterFragment()
            fragment.mainListener = navigationView
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        if(arguments != null) {
            arguments?.let {
                val skillType = object : TypeToken<ArrayList<Skill>>() {}.type
                val skillsJson = it.getString(SKILL_KEY)
                skills = gsonConverter.fromJson(skillsJson, skillType)

                val coursesType = object : TypeToken<ArrayList<Long>>() {}.type
                val courseJson = it.getString(COURSE_KEY)
                courses = gsonConverter.fromJson(courseJson, coursesType)
            }
        } else {
            loadCourses()
        }
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

        if(skills.size != 0) {
            tv_added_skills.text = getSkillsText()
        }
        if(courses.size != 0) {
            if(courses.size != 4) {
                tv_added_courses.text = getCoursesText()
            } else {
                tv_added_courses.text = getString(R.string.doesnt_matter)
            }
        }
    }

    private fun setToolbarData() {
        mainListener.setToolbar(toolbar_add_done)
        btn_add.visibility = View.GONE
    }

    private fun setListeners() {
        btn_back.setOnClickListener(this)
        btn_ok.setOnClickListener(this)
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

            R.id.btn_ok -> saveFilters()
        }
    }

    private fun saveFilters() {
        val intent = Intent()

        val skillsJson = gsonConverter.toJson(skills)
        intent.putExtra(SKILL_KEY, skillsJson)

        val coursesJson = gsonConverter.toJson(courses)
        intent.putExtra(COURSE_KEY, coursesJson)
        targetFragment?.onActivityResult(FILTERS, Activity.RESULT_OK, intent)
        mainListener.hideFragment()
    }

    private fun addSkill() {
        val fragment = ChooseSkillFragment.newInstance(mainListener)
        fragment.setTargetFragment(this, ADD_SKILL)
        mainListener.showFragment(SHOW_THEMES, this, fragment)
    }

    private fun chooseCourse() {
        for(year in courses) {
            selectedYears.add((year - 1).toInt())
        }
        this.activity?.let {
            MaterialDialog.Builder(it)
                .title(R.string.choose_course)
                .items(R.array.courses)
                .itemsCallbackMultiChoice(selectedYears.toTypedArray(), { dialog, which, text ->
                    /**
                     * If you use alwaysCallMultiChoiceCallback(), which is discussed below,
                     * returning false here won't allow the newly selected check box to actually be selected
                     * (or the newly unselected check box to be unchecked).
                     * See the limited multi choice dialog example in the sample project for details.
                     */
                    listCourses = (text.toList() as List<String>).toMutableList()
                    tv_added_courses.text = getListString(listCourses)
                    if(courses.size != 0) {
                        courses.clear()
                        selectedYears.clear()
                    }
                    selectedYears.addAll(which)
                    for(i in which.indices) {
                        courses.add((which[i] + 1).toLong())
                    }

                    if(courses.size == 4) {
                        tv_added_courses.text = getString(R.string.doesnt_matter)
                    }
//                    mainListener.showSnackBar(getListString(listCourses))
                    true
                })
                .positiveText(R.string.choose)
                .show()
        }
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

    override fun onActivityResult(reqCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(reqCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK) {

            when(reqCode) {

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
                            for (i in listSkills.indices) {
                                listSkills[i] = "${skills[i].name} ${getString(R.string.level)} ${skills[i].level}"
                            }
                            tv_added_skills.text = getListString(listSkills)
                        }
                    }
                }
            }
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
        listSkills.clear()
        for(i in skills.indices) {
            listSkills.add("${skills[i].name} ${getString(R.string.level)} ${skills[i].level}")
        }
        return getListString(listSkills)
    }

    private fun getCoursesText(): String {
        listCourses.clear()
        val courseArr = resources.getStringArray(R.array.courses)
        for(i in courses.indices) {
            listCourses.add(courseArr[(courses[i] - 1).toInt()])
        }
        return getListString(listCourses)
    }

    private fun loadCourses() {
        for(i in 1..4) {
            courses.add(i.toLong())
        }
    }
}