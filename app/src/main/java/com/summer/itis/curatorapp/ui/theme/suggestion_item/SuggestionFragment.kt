package com.summer.itis.curatorapp.ui.theme.suggestion_item

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.FOCUS_DOWN
import android.view.ViewGroup
import android.view.WindowManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.R.string.skills
import com.summer.itis.curatorapp.R.string.theme
import com.summer.itis.curatorapp.model.theme.SuggestionTheme
import com.summer.itis.curatorapp.model.theme.ThemeProgress
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragment
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationBaseActivity.Companion.SHOW_THEMES
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationBaseActivity.Companion.TAB_PROFILE
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationBaseActivity.Companion.TAB_THEMES
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationView
import com.summer.itis.curatorapp.ui.comment.CommentFragment
import com.summer.itis.curatorapp.ui.curator.curator_item.description.view.DescriptionFragment
import com.summer.itis.curatorapp.ui.student.student_item.StudentFragment
import com.summer.itis.curatorapp.ui.theme.add_theme.AddThemeFragment
import com.summer.itis.curatorapp.ui.theme.edit_suggestion.EditSuggestionFragment
import com.summer.itis.curatorapp.ui.theme.theme_item.ThemeFragment
import com.summer.itis.curatorapp.ui.theme.theme_item.ThemePresenter
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const.CURATOR_TYPE
import com.summer.itis.curatorapp.utils.Const.DESC_KEY
import com.summer.itis.curatorapp.utils.Const.EDIT_SUGGESTION
import com.summer.itis.curatorapp.utils.Const.ID_KEY
import com.summer.itis.curatorapp.utils.Const.STUDENT_TYPE
import com.summer.itis.curatorapp.utils.Const.SUGGESTION_TYPE
import com.summer.itis.curatorapp.utils.Const.TAG_LOG
import com.summer.itis.curatorapp.utils.Const.THEME_KEY
import com.summer.itis.curatorapp.utils.Const.TYPE
import com.summer.itis.curatorapp.utils.Const.USER_ID
import com.summer.itis.curatorapp.utils.Const.gsonConverter
import com.summer.itis.curatorapp.utils.SkillViewHelper
import kotlinx.android.synthetic.main.layout_add_comment.*
import kotlinx.android.synthetic.main.layout_expandable_text_view.*
import kotlinx.android.synthetic.main.layout_suggestion.*
import kotlinx.android.synthetic.main.toolbar_edit.*

class SuggestionFragment : CommentFragment<SuggestionPresenter>(), SuggestionView, View.OnClickListener {

    override lateinit var entityId: String

    override lateinit var mainListener: NavigationView
    lateinit var suggestionTheme: SuggestionTheme

    @InjectPresenter
    override lateinit var presenter: SuggestionPresenter

    companion object {

        const val TAG_CURATOR = "TAG_CURATOR"

        const val EDIT_CURATOR = 1

        fun newInstance(args: Bundle, navigationView: NavigationView): Fragment {
            val fragment = SuggestionFragment()
            fragment.arguments = args
            fragment.mainListener = navigationView
            return fragment
        }

        fun newInstance(navigationView: NavigationView): Fragment {
            val fragment = SuggestionFragment()
            fragment.mainListener = navigationView
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_suggestion, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        entityId = suggestionTheme.id
        presenter.loadComments(entityId)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainListener.hideBottomNavigation()
        mainListener.changeWindowsSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        arguments?.let {
            suggestionTheme = gsonConverter.fromJson(it.getString(THEME_KEY), SuggestionTheme::class.java)
        }
    }

    fun initViews() {
        setToolbarData()
        setListeners()
        setData()
        setBtnsVisibility()
    }

    private fun setBtnsVisibility() {
        val status = tv_status.text
        if(status.equals(getString(R.string.theme_on_revision))) {
            btn_revision.visibility = View.GONE
        }
        if(suggestionTheme.type.equals(STUDENT_TYPE)) {
            btn_edit.visibility = View.GONE
        }
    }

    private fun setToolbarData() {
        mainListener.setToolbar(toolbar_edit)
        suggestionTheme.themeProgress?.title?.let { mainListener.setToolbarTitle(it) }
        btn_back.visibility = View.VISIBLE

    }

    private fun setListeners() {
        btn_edit.setOnClickListener(this)
        btn_back.setOnClickListener(this)
        btn_accept.setOnClickListener(this)
        btn_add_reject.setOnClickListener(this)
        btn_revision.setOnClickListener(this)
        li_student.setOnClickListener(this)
        li_desc.setOnClickListener(this)
    }

    private fun setData() {

        if(suggestionTheme.theme?.skills?.size == 0) {
            li_skills.visibility = View.GONE
        } else {
            tv_skills.text = this.activity?.let { suggestionTheme.theme?.skills?.let { it1 -> SkillViewHelper.getSkillsText(it1, it) } }
        }
        tv_title.text = suggestionTheme.themeProgress?.title
        tv_curator.text = suggestionTheme.curator?.name
        tv_student.text = suggestionTheme.student?.name
        tv_subject.text = suggestionTheme.theme?.subject?.name
        tv_status.text = suggestionTheme.status
        expand_text_view.text = suggestionTheme.themeProgress?.description
    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.btn_back -> backFragment()

            R.id.btn_edit -> editProfile()

            R.id.li_student -> showStudent()

            R.id.li_desc -> showDesc()

            R.id.btn_accept -> acceptTheme()

            R.id.btn_add_reject -> rejectTheme()

            R.id.btn_revision -> revisionTheme()
        }
    }

    private fun showDesc() {
        val args = Bundle()
        args.putString(DESC_KEY, suggestionTheme.themeProgress?.description)
        args.putString(TYPE, SUGGESTION_TYPE)
        args.putString(USER_ID, AppHelper.currentCurator.id)
        args.putString(ID_KEY, suggestionTheme.id)
        val fragment = DescriptionFragment.newInstance(args, mainListener)
        mainListener.pushFragments(TAB_THEMES, fragment, true)
    }

    private fun editProfile() {
        val args = Bundle()
        args.putString(THEME_KEY, gsonConverter.toJson(suggestionTheme.themeProgress))
        val fragment = EditSuggestionFragment.newInstance(args, mainListener)
        fragment.setTargetFragment(this, EDIT_SUGGESTION)
        mainListener.showFragment(SHOW_THEMES, this, fragment)
    }

    private fun showStudent() {
        val args = Bundle()
        args.putString(USER_ID, suggestionTheme.studentId)
        val fragment = StudentFragment.newInstance(args, mainListener)
        mainListener.pushFragments(TAB_THEMES, fragment, true)
    }

    private fun acceptTheme() {
        suggestionTheme.status = getString(R.string.accept_theme)
        saveStatus()
        backFragment()
    }

    private fun rejectTheme() {
        suggestionTheme.status = getString(R.string.reject_theme)
        saveStatus()
        backFragment()
    }

    private fun revisionTheme() {
        suggestionTheme.status = getString(R.string.theme_on_revision)
        saveStatus()
    }

    private fun saveStatus() {
        val iterator = AppHelper.currentCurator.suggestions.iterator()
        for(sug in iterator) {
            if(sug.id.equals(suggestionTheme.id)) {
                if(suggestionTheme.status.equals(getString(R.string.theme_on_revision))) {
                    sug.status = suggestionTheme.status
                    tv_status.text = sug.status
                    btn_revision.visibility = View.GONE
                } else {
                    /*if(suggestionTheme.status.equals(getString(R.string.accept_theme))){
                        val theme = suggestionTheme.theme

                        theme?.let {
                            it.studentId = suggestionTheme.studentId
                            it.student = suggestionTheme.student
                            it.curator = suggestionTheme.curator
                            suggestionTheme.themeProgress?.subject?.let { subject ->  it.subject = subject }
                            AppHelper.currentCurator.themes.add(it)
                        }
                    } else {

                    }*/
//                    AppHelper.currentCurator.suggestions.remove(sug)
                    iterator.remove()
                }

            }
        }
        context?.let { AppHelper.saveCurrentState(AppHelper.currentCurator, it) }
    }

    override fun clearAfterSendComment() {
        scrollView.fullScroll(FOCUS_DOWN)
        et_comment.setText(null)
        view?.getRootView()?.let {
            AppHelper.hideKeyboardFrom(this.activity as Context, it)
            Log.d(TAG_LOG, "hide keyboard")
        }
        et_comment.clearFocus()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when(requestCode) {

                EDIT_SUGGESTION -> {
                    val json = data?.getStringExtra(THEME_KEY)
                    json?.let {
                        val themeProgress = gsonConverter.fromJson(json, ThemeProgress::class.java)
                        tv_subject.text = themeProgress.subject.name
                        tv_title.text = themeProgress.title
                        expand_text_view.text = themeProgress.description

                        suggestionTheme.themeProgress = themeProgress

                        mainListener.showSnackBar(getString(R.string.changes_updated))
                    }
                }
            }
        }
    }
}
