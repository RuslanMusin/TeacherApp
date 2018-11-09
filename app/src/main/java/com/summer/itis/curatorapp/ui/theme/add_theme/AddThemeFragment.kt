package com.summer.itis.curatorapp.ui.theme.add_theme

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
import com.summer.itis.curatorapp.model.skill.Subject
import com.summer.itis.curatorapp.model.theme.SuggestionTheme
import com.summer.itis.curatorapp.model.theme.Theme
import com.summer.itis.curatorapp.model.user.Student
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragment
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationBaseActivity.Companion.SHOW_THEMES
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationView
import com.summer.itis.curatorapp.ui.student.student_list.StudentListFragment
import com.summer.itis.curatorapp.ui.subject.add_subject.AddSubjectFragment
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const.ADD_THEME_TYPE
import com.summer.itis.curatorapp.utils.Const.EDIT_SUGGESTION
import com.summer.itis.curatorapp.utils.Const.ID_KEY
import com.summer.itis.curatorapp.utils.Const.SUBJECT_KEY
import com.summer.itis.curatorapp.utils.Const.SUGGESTION_TYPE
import com.summer.itis.curatorapp.utils.Const.TAG_LOG
import com.summer.itis.curatorapp.utils.Const.THEME_KEY
import com.summer.itis.curatorapp.utils.Const.TYPE
import com.summer.itis.curatorapp.utils.Const.USER_KEY
import com.summer.itis.curatorapp.utils.Const.gsonConverter
import kotlinx.android.synthetic.main.fragment_add_theme.*
import kotlinx.android.synthetic.main.toolbar_add.*
import java.util.*

class AddThemeFragment : BaseFragment<AddThemePresenter>(), AddThemeView, View.OnClickListener {

    private lateinit var theme: Theme
    private var suggestionTheme: SuggestionTheme? = null

    private var type = ADD_THEME_TYPE

    override lateinit var mainListener: NavigationView

    private var studentId: String? = null
    private var subject: Subject? = null
    private var student: Student? = null

    @InjectPresenter
    lateinit var presenter: AddThemePresenter

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
                btn_create_questions.setText(getString(R.string.save_changes))
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
    }

    private fun setToolbarData() {
        mainListener.setToolbar(toolbar_add)
        btn_add.visibility = View.GONE
    }

    private fun setListeners() {
        btn_create_questions.setOnClickListener(this)
        btn_add_subject.setOnClickListener(this)
        btn_add_student.setOnClickListener(this)
        btn_back.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.btn_create_questions -> {
                if(validateData()) {
                    theme.title = et_theme_name.text.toString()
                    theme.description = et_theme_desc.text.toString()
                    theme.curatorId = AppHelper.currentCurator.id
                    studentId?.let { theme.studentId = it }
                    theme.curator = AppHelper.currentCurator
                    theme.student = null
                    subject?.let {
                        theme.subjectId = it.id
                        theme.subject = it
                    }
                    theme.dateCreation = Date()
                    context?.let { presenter.saveChanges(theme, type, it) }
//                    backFragment()
                } else {
                    mainListener.showSnackBar(getString(R.string.invalid_fields))
                }
            }

            R.id.btn_add_subject -> {
                val fragment = AddSubjectFragment.newInstance(mainListener)
                fragment.setTargetFragment(this, ADD_SUBJECT)
                mainListener.showFragment(SHOW_THEMES, this, fragment)
//                mainListener.pushFragments(TAB_STUDENTS, fragment, false)
//                mainListener.loadFragment(fragment)

            }

            R.id.btn_add_student -> {
                val fragment = StudentListFragment.newInstance(mainListener)
                fragment.setTargetFragment(this, ADD_SUBJECT)
                mainListener.showFragment(SHOW_THEMES, this, fragment)
            }

            R.id.btn_back -> backFragment()
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
                        tv_added_subject.text = subject?.name
                    }
                }

                ADD_STUDENT -> {
                    data?.getStringExtra(USER_KEY)?.let {
                        student = gsonConverter.fromJson(it, Student::class.java)
                        tv_added_student.text = student?.getFullName()
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
}
