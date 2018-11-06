package com.summer.itis.curatorapp.ui.work.one_work_list

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.*
import com.arellomobile.mvp.presenter.InjectPresenter
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.skill.Skill
import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.model.user.Person
import com.summer.itis.curatorapp.model.user.Student
import com.summer.itis.curatorapp.model.work.Work
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragment
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationView
import com.summer.itis.curatorapp.ui.skill.skill_list.edit.EditSkillsFragment
import com.summer.itis.curatorapp.ui.skill.skill_list.view.SkillAdapter
import com.summer.itis.curatorapp.utils.Const
import com.summer.itis.curatorapp.utils.Const.CURATOR_TYPE
import com.summer.itis.curatorapp.utils.Const.OWNER_TYPE
import com.summer.itis.curatorapp.utils.Const.PERSON_TYPE
import com.summer.itis.curatorapp.utils.Const.STUDENT_TYPE
import com.summer.itis.curatorapp.utils.Const.WATCHER_TYPE
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.layout_recycler_list.*
import kotlinx.android.synthetic.main.toolbar_edit.*
import java.util.regex.Pattern

class WorkListFragment : BaseFragment<WorkListPresenter>(), WorkListView, View.OnClickListener {

    lateinit var user: Person
    var personType: String = CURATOR_TYPE
    var type: String = OWNER_TYPE
    lateinit override var mainListener: NavigationView
    private lateinit var adapter: WorkAdapter

    lateinit var skills: MutableList<Work>

    @InjectPresenter
    lateinit var presenter: WorkListPresenter

    companion object {

        const val TAG_SKILLS = "TAG_SKILLS"

        const val EDIT_SKILLS = 1

        fun newInstance(args: Bundle, navigationView: NavigationView): Fragment {
            val fragment = WorkListFragment()
            fragment.arguments = args
            fragment.mainListener = navigationView
            return fragment
        }

        fun newInstance(navigationView: NavigationView): Fragment {
            val fragment = WorkListFragment()
            fragment.mainListener = navigationView
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {
            personType = it.getString(PERSON_TYPE)
            if (personType.equals(STUDENT_TYPE)) {
                type = WATCHER_TYPE
                user = Const.gsonConverter.fromJson(it.getString(Const.USER_KEY), Student::class.java)
            } else {
                user = Const.gsonConverter.fromJson(it.getString(Const.USER_KEY), Curator::class.java)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_skills, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        loadSkills()
    }

    private fun loadSkills() {
//        presenter.loadSkills(AppHelper.currentCurator.id)
        skills = ArrayList()

        for(i in 1..10) {
            var work: Work = Work()
            if(i % 2 == 0) {
                work.name = "Machine Learning Project $i"
            } else {
                work.name = "Java Project $i"
            }
            skills.add(work)
        }
        changeDataSet(skills)
    }

    private fun initViews() {
        setToolbarData()
        initRecycler()
        setListeners()
    }

    private fun setToolbarData() {
        mainListener.setToolbar(toolbar_edit)
        btn_back.visibility = View.VISIBLE
        btn_edit.visibility = View.GONE
    }

    private fun setListeners() {
//        btn_edit.setOnClickListener(this)
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


    override fun changeDataSet(tests: List<Work>) {
        adapter.changeDataSet(tests)
        hideLoading()
    }

    override fun handleError(throwable: Throwable) {

    }

    private fun initRecycler() {
        adapter = WorkAdapter(ArrayList())
        val manager = LinearLayoutManager(activity as Activity)
        rv_list.layoutManager = manager
        rv_list.setEmptyView(tv_empty)
        adapter.attachToRecyclerView(rv_list)
        adapter.setOnItemClickListener(this)
    }

    override fun onItemClick(item: Work) {
//        TestActivity.start(this, item)
    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.btn_edit -> editSkills()

            R.id.btn_back -> backFragment()

        }
    }

    private fun editSkills() {
       /* val fragment = EditSkillsFragment.newInstance(mainListener)
        fragment.setTargetFragment(this, EDIT_SKILLS)
        mainListener.loadFragment(fragment)*/
    }

   /* override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when(requestCode) {

              EDIT_SKILLS -> changeDataSet(user.subjects)
            }
        }
    }*/

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

    }

    private fun findFromList(query: String) {
        val pattern: Pattern = Pattern.compile("${query.toLowerCase()}.*")
        val list: MutableList<Work> = java.util.ArrayList()
        for(skill in skills) {
            if(pattern.matcher(skill.name.toLowerCase()).matches()) {
                list.add(skill)
            }
        }
        changeDataSet(list)
    }


}
