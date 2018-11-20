package com.summer.itis.curatorapp.ui.theme.theme_list.tab_fragment.my_theme_list

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afollestad.materialdialogs.MaterialDialog
import com.arellomobile.mvp.presenter.InjectPresenter
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.R.drawable.student
import com.summer.itis.curatorapp.model.skill.Skill
import com.summer.itis.curatorapp.model.skill.Subject
import com.summer.itis.curatorapp.model.theme.SuggestionTheme
import com.summer.itis.curatorapp.model.theme.Theme
import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.model.user.Student
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragment
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationBaseActivity
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationBaseActivity.Companion.SHOW_THEMES
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationView
import com.summer.itis.curatorapp.ui.student.student_list.StudentListFragment
import com.summer.itis.curatorapp.ui.theme.add_theme.AddThemeFragment
import com.summer.itis.curatorapp.ui.theme.suggestion_item.SuggestionFragment
import com.summer.itis.curatorapp.ui.theme.theme_item.ThemeFragment
import com.summer.itis.curatorapp.ui.theme.theme_list.tab_fragment.suggestion_list.SuggestionAdapter
import com.summer.itis.curatorapp.ui.theme.theme_list.tab_fragment.suggestion_list.SuggestionListFragment
import com.summer.itis.curatorapp.ui.theme.theme_list.tab_fragment.suggestion_list.SuggestionListPresenter
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const
import com.summer.itis.curatorapp.utils.Const.ALL_CHOOSED
import com.summer.itis.curatorapp.utils.Const.REQUEST_CODE
import com.summer.itis.curatorapp.utils.Const.SEND_THEME
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_theme_list.*
import kotlinx.android.synthetic.main.layout_recycler_list.*
import java.util.regex.Pattern

class MyThemeListFragment : BaseFragment<MyThemeListPresenter>(), MyThemeListView, View.OnClickListener {

    lateinit var user: Curator
    lateinit override var mainListener: NavigationView
    private lateinit var adapter: ThemeAdapter

    lateinit var themes: MutableList<Theme>

    @InjectPresenter
    lateinit var presenter: MyThemeListPresenter

    private var fakeStudentNum = 0

    companion object {

        const val TAG_SKILLS = "TAG_SKILLS"

        const val EDIT_SKILLS = 1

        fun newInstance(args: Bundle, navigationView: NavigationView): Fragment {
            val fragment = MyThemeListFragment()
            fragment.arguments = args
            fragment.mainListener = navigationView
            return fragment
        }

        fun newInstance(navigationView: NavigationView): Fragment {
            val fragment = MyThemeListFragment()
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
//        presenterOne.loadSkills(AppHelper.currentCurator.id)
        if(user.themes.size == 0) {
            themes = ArrayList()
            val skills = this.activity?.let { AppHelper.getSkillsList(it) }
            for (i in 1..10) {
                val theme = Theme()
                theme.id = "$i"
                val curator = AppHelper.currentCurator
                theme.curator = curator
                theme.curatorId = curator.id
//                suggestionTheme.curatorName = user.name
                theme.student = null
                theme.studentId = i.toString()
                val lastNum = if(10 - i > 3)  i + 3 else i + 10 - i
                theme.skills = skills?.subList(i, lastNum) as MutableList<Skill>

                theme.description = "Simple App for students"
                if(i % 2 == 0) {
                    theme.title = "Web-платформа для создания интеллектуальных систем"
                    val subject = Subject()
                    subject.name = "Интеллектуальные системы"
                    subject.id = "$i"
                    theme.subject = subject
                    theme.subjectId = subject.id
                } else {
                    theme.title = "Приложение для обмена книгами"
                    val subject = Subject()
                    subject.name = "Android"
                    subject.id = "$i"
                    theme.subject = subject
                    theme.subjectId = subject.id
                }
                themes.add(theme)
            }
            user.themes = themes

        } else {
            themes = user.themes
        }
        changeDataSet(themes)

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


    override fun changeDataSet(tests: List<Theme>) {
        adapter.changeDataSet(tests)
        hideLoading()
    }

    override fun handleError(throwable: Throwable) {

    }

    private fun initRecycler() {
        adapter = ThemeAdapter(ArrayList(), this)
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

    override fun onItemClick(item: Theme) {
        val args = Bundle()
        args.putString(Const.THEME_KEY, Const.gsonConverter.toJson(item))
        val fragment = ThemeFragment.newInstance(args, mainListener)
        mainListener.pushFragments(NavigationBaseActivity.TAB_THEMES, fragment, true)
    }

    override fun openStudentAction(adapterPosition: Int) {
        val theme = themes[adapterPosition]
        if(theme.targetType.equals(ALL_CHOOSED)) {
            this.activity?.let {
                MaterialDialog.Builder(it)
                        .title(R.string._should_user_fake_choose_theme)
                        .items(R.array.fake_students)
                        .itemsCallbackSingleChoice(0, MaterialDialog.ListCallbackSingleChoice { dialog, view, which, text ->
                            fakeStudentNum = which
                            true
                        })
                        .alwaysCallSingleChoiceCallback()
                        .positiveText(R.string.button_ok)
                        .negativeText(R.string.cancel)
                        .onNegative { dialog, which ->
                            dialog.dismiss()
                        }
                        .onPositive { dialog, which ->
                            this.context?.let { it1 ->
                                presenter.addFakeSuggestion(themes[adapterPosition], AppHelper.getFakeStudent(fakeStudentNum, it1))
                            }
                        }
                        .show()
            }
        } else {
            this.activity?.let {
                MaterialDialog.Builder(it)
                        .title(R.string.student_fake_theme)
                        .content(R.string.should_user_send_fake_theme)
                        .positiveText(R.string.button_ok)
                        .negativeText(R.string.cancel)
                        .onNegative { dialog, which ->
                            dialog.dismiss()
                        }
                        .onPositive { dialog, which ->
                            this.context?.let { it1 ->
                                presenter.addFakeStudentSuggestion(themes[adapterPosition], AppHelper.getFakeStudent(fakeStudentNum, it1))
                            }
                        }
                        .show()
            }
        }
    }

    override fun findByQuery(query: String) {
        val pattern: Pattern = Pattern.compile("${query.toLowerCase()}.*")
        val list: MutableList<Theme> = java.util.ArrayList()
        for(skill in themes) {
            if(pattern.matcher(skill.title.toLowerCase()).matches()) {
                list.add(skill)
            }
        }
        changeDataSet(list)
    }

    override fun onClick(view: View) {
        when(view.id) {

            R.id.btn_add_theme -> {

                val args = Bundle()
                args.putString(Const.ID_KEY, user.id)
                val fragment = AddThemeFragment.newInstance(args, mainListener)
              /*  val tabLayout = parentFragment?.view?.findViewById<TabLayout>(R.id.tab_layout)
                tabLayout?.visibility = View.GONE*/
                mainListener.pushFragments(NavigationBaseActivity.TAB_THEMES, fragment, true)            }
        }
    }
}
