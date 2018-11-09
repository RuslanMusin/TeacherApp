package com.summer.itis.curatorapp.ui.subject.add_subject

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.*
import com.arellomobile.mvp.presenter.InjectPresenter
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.skill.Subject
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragment
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationView
import com.summer.itis.curatorapp.ui.theme.add_theme.AddThemeFragment.Companion.ADD_SUBJECT
import com.summer.itis.curatorapp.utils.Const.SUBJECT_KEY
import com.summer.itis.curatorapp.utils.Const.gsonConverter
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.layout_recycler_list.*
import kotlinx.android.synthetic.main.toolbar_add.*
import java.util.regex.Pattern

class AddSubjectFragment : BaseFragment<AddSubjectPresenter>(), AddSubjectView, View.OnClickListener {

    lateinit override var mainListener: NavigationView
    private lateinit var adapter: AddSubjectAdapter

    lateinit var subjects: MutableList<Subject>

    @InjectPresenter
    lateinit var presenter: AddSubjectPresenter

    companion object {

        const val TAG_SKILLS = "TAG_SKILLS"

        fun newInstance(args: Bundle, navigationView: NavigationView): Fragment {
            val fragment = AddSubjectFragment()
            fragment.arguments = args
            fragment.mainListener = navigationView
            return fragment
        }

        fun newInstance(navigationView: NavigationView): Fragment {
            val fragment = AddSubjectFragment()
            fragment.mainListener = navigationView
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        mainListener.hideBottomNavigation()
        val view = inflater.inflate(R.layout.fragment_student_list, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        loadSkills()
    }

    private fun loadSkills() {
        changeDataSet(presenter.loadSubjects())
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


    override fun changeDataSet(tests: List<Subject>) {
        adapter.changeDataSet(tests)
        hideLoading()
    }

    override fun handleError(throwable: Throwable) {

    }

    private fun initRecycler() {
        adapter = AddSubjectAdapter(ArrayList())
        val manager = LinearLayoutManager(activity as Activity)
        rv_list.layoutManager = manager
        rv_list.setEmptyView(tv_empty)
        adapter.attachToRecyclerView(rv_list)
        adapter.setOnItemClickListener(this)
    }

    override fun onItemClick(item: Subject) {
        val intent = Intent()
        val itemJson = gsonConverter.toJson(item)
        intent.putExtra(SUBJECT_KEY, itemJson)

        targetFragment?.onActivityResult(ADD_SUBJECT,RESULT_OK, intent)
        mainListener.hideFragment()
//        mainListener.setResultAndBack(args)
    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.btn_back -> {
                mainListener.hideFragment()
//                backFragment()
            }

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
                return false
            }
        })

    }

    private fun findFromList(query: String) {
        val pattern: Pattern = Pattern.compile("${query.toLowerCase()}.*")
        val list: MutableList<Subject> = java.util.ArrayList()
        for(skill in subjects) {
            if(pattern.matcher(skill.name.toLowerCase()).matches()) {
                list.add(skill)
            }
        }
        changeDataSet(list)
    }
}