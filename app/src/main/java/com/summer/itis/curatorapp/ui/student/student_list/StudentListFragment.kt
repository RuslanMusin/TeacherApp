package com.summer.itis.curatorapp.ui.student.student_list

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.*
import com.arellomobile.mvp.presenter.InjectPresenter
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.user.Student
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragment
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationBaseActivity.Companion.SHOW_THEMES
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationView
import com.summer.itis.curatorapp.ui.student.student_item.StudentFragment
import com.summer.itis.curatorapp.ui.theme.add_theme.AddThemeFragment
import com.summer.itis.curatorapp.ui.theme.add_theme.AddThemeFragment.Companion.ADD_STUDENT
import com.summer.itis.curatorapp.utils.Const.STUDENT_TYPE
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.layout_recycler_list.*
import kotlinx.android.synthetic.main.toolbar_add.*
import java.util.regex.Pattern

class StudentListFragment : BaseFragment<StudentListPresenter>(), StudentListView, View.OnClickListener {

    lateinit override var mainListener: NavigationView
    private lateinit var adapter: StudentAdapter

    lateinit var students: MutableList<Student>

    @InjectPresenter
    lateinit var presenter: StudentListPresenter

    companion object {

        const val TAG_SKILLS = "TAG_SKILLS"

        const val EDIT_SKILLS = 1

        fun newInstance(args: Bundle, navigationView: NavigationView): Fragment {
            val fragment = StudentListFragment()
            fragment.arguments = args
            fragment.mainListener = navigationView
            return fragment
        }

        fun newInstance(navigationView: NavigationView): Fragment {
            val fragment = StudentListFragment()
            fragment.mainListener = navigationView
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_student_list, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        loadSkills()
    }

    private fun loadSkills() {
//        presenter.loadStudents()
        students = ArrayList()
        var student = Student()



        for(i in 1..10) {
            student.id = i.toString()
            if(i % 2 == 0) {
                student.name = "Ruslan"
                student.lastname = "Musin"
                student.patronymic = "Martovich"
                student.description = "usual desc"
                student.groupNumber = "11-603"
                student.year = 3
            } else {
                student.name = "Azat"
                student.lastname = "Alekbaev"
                student.patronymic = "Azatovich"
                student.description = "usual desc"
                student.groupNumber = "11-605"
                student.year = 3
            }
            students.add(student)
        }

        changeDataSet(students)
    }

    private fun initViews() {
        setToolbarData()
        initRecycler()
        setListeners()
    }

    private fun setToolbarData() {
        mainListener.setToolbar(toolbar_add)
        btn_add.visibility = View.GONE
    }

    private fun setListeners() {
        btn_back.setOnClickListener(this)
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


    override fun changeDataSet(tests: List<Student>) {
        adapter.changeDataSet(tests)
        hideLoading()
    }

    override fun handleError(throwable: Throwable) {

    }

    private fun initRecycler() {
        adapter = StudentAdapter(ArrayList())
        val manager = LinearLayoutManager(activity as Activity)
        rv_list.layoutManager = manager
        rv_list.setEmptyView(tv_empty)
        adapter.attachToRecyclerView(rv_list)
        adapter.setOnItemClickListener(this)
    }

    override fun onItemClick(item: Student) {
        val fragment = StudentFragment.newInstance(argUser(item, STUDENT_TYPE),mainListener)
        fragment.setTargetFragment(this, AddThemeFragment.ADD_SUBJECT)
        mainListener.showFragment(SHOW_THEMES, this, fragment)
//        mainListener.pushFragments(TAB_STUDENTS, fragment, true)
    }

    override fun onClick(v: View) {

        when(v.id) {

            R.id.btn_back -> backFragment()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.search_menu, menu)
        menu?.let { setSearchMenuItem(it) }
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setSearchMenuItem(menu: Menu) {
        val searchItem = menu.findItem(R.id.action_search)

        val searchView: SearchView = searchItem.actionView as SearchView
        val finalSearchView = searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
//                presenter.loadOfficialTestsByQUery(query)
                findFromList(query)

                if (!finalSearchView.isIconified) {
                    finalSearchView.isIconified = true
                }
                searchItem.collapseActionView()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                findFromList(newText)
                return false
            }
        })

        val filterItem = menu.findItem(R.id.action_filter)
        filterItem.setOnMenuItemClickListener {

        }

    }

    private fun findFromList(query: String) {
        val pattern: Pattern = Pattern.compile("${query.toLowerCase()}.*")
        val list: MutableList<Student> = java.util.ArrayList()
        for(skill in students) {
            if(pattern.matcher(skill.name.toLowerCase()).matches()) {
                list.add(skill)
            }
        }
        changeDataSet(list)
    }

    override fun onActivityResult(reqCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(reqCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK) {

            when(reqCode) {

                ADD_STUDENT -> {
                    targetFragment?.onActivityResult(ADD_STUDENT, Activity.RESULT_OK, data)
                    mainListener.hideFragment()
                }
            }
        }
    }
}
