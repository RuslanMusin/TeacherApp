package com.summer.itis.curatorapp.ui.theme.theme_list.tab_fragment.suggestion_list

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.arellomobile.mvp.presenter.InjectPresenter
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.R.string.skills
import com.summer.itis.curatorapp.model.skill.Skill
import com.summer.itis.curatorapp.model.theme.SuggestionTheme
import com.summer.itis.curatorapp.model.theme.Theme
import com.summer.itis.curatorapp.model.theme.ThemeProgress
import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragment
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationView
import com.summer.itis.curatorapp.ui.theme.theme_list.ThemeListView

import com.summer.itis.curatorapp.utils.AppHelper

import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.layout_recycler_list.*
import java.util.*
import java.util.regex.Pattern
import android.support.v7.widget.RecyclerView
import com.summer.itis.curatorapp.model.skill.Subject
import com.summer.itis.curatorapp.model.user.Student
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationBaseActivity.Companion.TAB_STUDENTS
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationBaseActivity.Companion.TAB_THEMES
import com.summer.itis.curatorapp.ui.student.student_item.StudentFragment
import com.summer.itis.curatorapp.ui.theme.add_theme.AddThemeFragment
import com.summer.itis.curatorapp.ui.theme.suggestion_item.SuggestionFragment
import com.summer.itis.curatorapp.utils.Const.CURATOR_TYPE
import com.summer.itis.curatorapp.utils.Const.ID_KEY
import com.summer.itis.curatorapp.utils.Const.STUDENT_TYPE
import com.summer.itis.curatorapp.utils.Const.THEME_KEY
import com.summer.itis.curatorapp.utils.Const.gsonConverter
import kotlinx.android.synthetic.main.fragment_theme_list.*
import kotlinx.android.synthetic.main.layout_suggestion.*


class SuggestionListFragment : BaseFragment<SuggestionListPresenter>(), SuggestionListView, View.OnClickListener {

    lateinit var user: Curator
    lateinit override var mainListener: NavigationView
    lateinit var fragmentParent: ThemeListView
    private lateinit var adapter: SuggestionAdapter

    lateinit var suggestions: MutableList<SuggestionTheme>

    @InjectPresenter
    lateinit var presenter: SuggestionListPresenter

    companion object {

        const val TAG_SKILLS = "TAG_SKILLS"

        const val EDIT_SKILLS = 1

        fun newInstance(args: Bundle, navigationView: NavigationView): Fragment {
            val fragment = SuggestionListFragment()
            fragment.arguments = args
            fragment.mainListener = navigationView
            return fragment
        }

        fun newInstance(navigationView: NavigationView): Fragment {
            val fragment = SuggestionListFragment()
            fragment.mainListener = navigationView
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_theme_list, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user = AppHelper.currentCurator
        initViews()
        loadSkills()
    }

    private fun loadSkills() {
//        presenter.loadSkills(AppHelper.currentCurator.id)
        if(user.suggestions.size == 0) {
            suggestions = ArrayList()

            for (i in 1..10) {
                val suggestionTheme = SuggestionTheme()
                suggestionTheme.id = "$i"

                val curator = AppHelper.currentCurator
                suggestionTheme.curator = curator
                suggestionTheme.curatorId = curator.id

                val student = Student()
                student.name = "Ruslan"
                student.lastname = "Musin"
                student.patronymic = "Maratovich"
                suggestionTheme.student = student
                suggestionTheme.studentId = i.toString()

                suggestionTheme.theme = Theme()
                suggestionTheme.theme?.id = "$i"
                suggestionTheme.themeId = "$i"

                suggestionTheme.themeProgress = ThemeProgress()
                suggestionTheme.themeProgress?.id = "$i"
                suggestionTheme.themeProgressId = "$i"
                suggestionTheme.themeProgress?.description = "Simple App for students"
                suggestionTheme.theme?.description = "Simple App for students"
                if(i % 2 == 0) {
                    val title = "Приложение для обмена книгами $i"
                    suggestionTheme.theme?.title = title
                    suggestionTheme.themeProgress?.title = title

                    val subject = Subject()
                    subject.name = "Android"
                    subject.id  = "$i"
                    suggestionTheme.themeProgress?.subject = subject
                    suggestionTheme.status = getString(R.string.theme_on_revision)
                    suggestionTheme.type = CURATOR_TYPE
                    suggestionTheme.themeProgress?.subject = subject
                    suggestionTheme.theme?.subject = subject
                } else {
                    val title = "Web-платформа для создания интеллектуальных систем $i"
                    suggestionTheme.theme?.title = title
                    suggestionTheme.themeProgress?.title = title

                    val subject = Subject()
                    subject.id  = "$i"
                    subject.name = "Интеллектуальные системы"
                    suggestionTheme.themeProgress?.subject = subject
                    suggestionTheme.status = getString(R.string.theme_is_new)
                    suggestionTheme.themeProgress?.subject = subject
                    suggestionTheme.theme?.subject = subject
                }
                suggestions.add(suggestionTheme)
            }
            user.suggestions = suggestions

        } else {
            suggestions = user.suggestions
        }
        changeDataSet(suggestions)

    }

    private fun initViews() {
        initRecycler()
        setListeners()
    }

    private fun setListeners() {
        btn_add_theme.setOnClickListener(this)

    }

    override fun setNotLoading() {

    }

    override fun showLoading(disposable: Disposable) {
        pb_list.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        pb_list.visibility = View.GONE
    }

    override fun loadNextElements(i: Int) {
    }


    override fun changeDataSet(tests: List<SuggestionTheme>) {
        adapter.changeDataSet(tests)
        hideLoading()
    }

    override fun handleError(throwable: Throwable) {

    }

    private fun initRecycler() {
        adapter = SuggestionAdapter(ArrayList())
        val manager = LinearLayoutManager(activity as Activity)
        rv_list.layoutManager = manager
        rv_list.setEmptyView(tv_empty)
        adapter.attachToRecyclerView(rv_list)
        adapter.setOnItemClickListener(this)

        rv_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 || dy < 0 && btn_add_theme.isShown())
                    btn_add_theme.hide()
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    btn_add_theme.show()
                }
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }

    override fun onItemClick(item: SuggestionTheme) {
        val args = Bundle()
        args.putString(THEME_KEY, gsonConverter.toJson(item))
        val fragment = SuggestionFragment.newInstance(args, mainListener)
        mainListener.pushFragments(TAB_THEMES, fragment, true)
    }

    override fun findByQuery(query: String) {
        val pattern: Pattern = Pattern.compile("${query.toLowerCase()}.*")
        val list: MutableList<SuggestionTheme> = java.util.ArrayList()
        for(skill in suggestions) {
            if(pattern.matcher(skill.themeProgress?.title?.toLowerCase()).matches()) {
                list.add(skill)
            }
        }
        changeDataSet(list)
    }

    override fun onClick(view: View) {
        when(view.id) {

            R.id.btn_add_theme -> {
                val args = Bundle()
                args.putString(ID_KEY, user.id)
                val fragment = AddThemeFragment.newInstance(args, mainListener)
                mainListener.pushFragments(TAB_THEMES, fragment, true)
            }
        }
    }
}
