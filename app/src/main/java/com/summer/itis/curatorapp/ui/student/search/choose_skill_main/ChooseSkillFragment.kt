package com.summer.itis.curatorapp.ui.student.search.choose_skill_main

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.R.drawable.item
import com.summer.itis.curatorapp.model.skill.Skill
import com.summer.itis.curatorapp.model.user.Student
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragment
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationBaseActivity.Companion.SHOW_THEMES
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationView
import com.summer.itis.curatorapp.ui.student.search.choose_skill.ChooseAddSkillFragment
import com.summer.itis.curatorapp.ui.student.student_list.StudentAdapter
import com.summer.itis.curatorapp.ui.student.student_list.StudentListPresenter
import com.summer.itis.curatorapp.utils.Const.ADD_SKILL
import com.summer.itis.curatorapp.utils.Const.SKILL_KEY
import com.summer.itis.curatorapp.utils.Const.gsonConverter
import kotlinx.android.synthetic.main.fragment_choose_skill.*
import kotlinx.android.synthetic.main.toolbar_choose.*

class ChooseSkillFragment : BaseFragment<ChooseSkillMainPresenter>(), ChooseSkillMainView, View.OnClickListener {

    lateinit override var mainListener: NavigationView
    private lateinit var adapter: StudentAdapter

    lateinit var students: MutableList<Student>

    @InjectPresenter
    lateinit var presenter: ChooseSkillMainPresenter

    private var level: String? = null
    private var skillName: String? = null
    private var skill: Skill? = null

    companion object {

        const val TAG_SKILLS = "TAG_SKILLS"

        const val CHOOSE_SKILL = 1

        const val CHOOSE_LEVEL = 2

        fun newInstance(args: Bundle, navigationView: NavigationView): Fragment {
            val fragment = ChooseSkillFragment()
            fragment.arguments = args
            fragment.mainListener = navigationView
            return fragment
        }

        fun newInstance(navigationView: NavigationView): Fragment {
            val fragment = ChooseSkillFragment()
            fragment.mainListener = navigationView
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_choose_skill, container, false)
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
        mainListener.setToolbar(toolbar_choose)
    }

    private fun setListeners() {
        btn_cancel.setOnClickListener(this)
        btn_ok.setOnClickListener(this)
      /*  spinner_skill.setItems("Выберите компетенцию")
        spinner_skill.setOnClickListener {
            val fragment = ChooseAddSkillFragment.newInstance(mainListener)
            fragment.setTargetFragment(this, CHOOSE_SKILL)
            mainListener.showFragment(SHOW_THEMES, this, fragment)
        }*/
        tv_skill.text = "Выберите компетенцию"
        li_choose_skill.setOnClickListener{
            val fragment = ChooseAddSkillFragment.newInstance(mainListener)
            fragment.setTargetFragment(this, CHOOSE_SKILL)
            mainListener.showFragment(SHOW_THEMES, this, fragment)
        }
        spinner_level.setItems(resources.getStringArray(R.array.skill_levels).toList())
        level = getString(R.string.low_level)
        spinner_level.setOnItemSelectedListener { view, position, id, item ->
            level = item as String
        }
    }

    override fun onClick(v: View) {

        when(v.id) {

            R.id.btn_cancel -> backFragment()

            R.id.btn_ok -> addSkill()
        }
    }

    private fun addSkill() {
        val intent = Intent()
        level?.let { skill?.level = it }
        val skillJson = gsonConverter.toJson(skill)
        intent.putExtra(SKILL_KEY, skillJson)
        targetFragment?.onActivityResult(ADD_SKILL, RESULT_OK, intent)
        mainListener.hideFragment()
    }

    override fun onActivityResult(reqCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(reqCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK) {

            when(reqCode) {

                CHOOSE_SKILL -> {
                    val skillJson = data?.getStringExtra(SKILL_KEY)
                    skillJson?.let {
                        skill = gsonConverter.fromJson(skillJson, Skill::class.java)
                        tv_skill.text = skill?.name
//                        spinner_skill.setItems(skill?.name)
                    }
                }
            }
        }
    }
}