package com.summer.itis.curatorapp.ui.skill.skill_list.edit.add_skill

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
import com.summer.itis.curatorapp.model.skill.Skill
import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragment
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationView
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const.ADD_SKILL
import com.summer.itis.curatorapp.utils.Const.OWNER_TYPE
import com.summer.itis.curatorapp.utils.Const.SKILL_KEY
import com.summer.itis.curatorapp.utils.Const.gsonConverter
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.layout_recycler_list.*
import kotlinx.android.synthetic.main.toolbar_edit.*
import java.util.*
import java.util.regex.Pattern

class AddSkillFragment : BaseFragment<AddSkillPresenter>(), AddSkillView, View.OnClickListener {

    lateinit var user: Curator
    var type: String = OWNER_TYPE
    lateinit override var mainListener: NavigationView
    private lateinit var adapter: AddSkillAdapter

    lateinit var skills: MutableList<Skill>

    @InjectPresenter
    lateinit var presenter: AddSkillPresenter

    companion object {

        const val TAG_SKILLS = "TAG_SKILLS"

        fun newInstance(args: Bundle, navigationView: NavigationView): Fragment {
            val fragment = AddSkillFragment()
            fragment.arguments = args
            fragment.mainListener = navigationView
            return fragment
        }

        fun newInstance(navigationView: NavigationView): Fragment {
            val fragment = AddSkillFragment()
            fragment.mainListener = navigationView
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        user = AppHelper.currentCurator
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
//        presenterOne.loadSkills(AppHelper.currentCurator.id)
        this.activity?.let { skills = AppHelper.getSkillsList(it).toMutableList() }
       /* var skill: Skill = Skill()

        skill.name = "Java"
        skill.id = "101"
        skill.level = getString(R.string.medium_level)
        skills.add(skill)

        var level: Int
        var levelStr: String = getString(R.string.low_level)
        for(i in 1..10) {
            skill = Skill()
            skill.id = "$i"
            if(i % 2 == 0) {
                skill.level = getString(R.string.low_level)
                skill.name = "Machine Learning $i"
            } else {
                skill.level = getString(R.string.high_level)
                skill.name = "Android $i"
            }
            level = Random().nextInt(3)
            this.activity?.let { levelStr = AppHelper.getLevelStr(level, it) }
            skill.level = levelStr
            skills.add(skill)
        }*/

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
        btn_ok.visibility = View.GONE
        btn_edit.visibility = View.GONE
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


    override fun changeDataSet(tests: List<Skill>) {
        adapter.changeDataSet(tests)
        hideLoading()
    }

    override fun handleError(throwable: Throwable) {

    }

    private fun initRecycler() {
        adapter = AddSkillAdapter(ArrayList())
        val manager = LinearLayoutManager(activity as Activity)
        rv_list.layoutManager = manager
        rv_list.setEmptyView(tv_empty)
        adapter.attachToRecyclerView(rv_list)
        adapter.setOnItemClickListener(this)
    }

    override fun onItemClick(item: Skill) {
        val intent = Intent()
        val skillJson = gsonConverter.toJson(item)
        intent.putExtra(SKILL_KEY, skillJson)
        targetFragment?.onActivityResult(ADD_SKILL, RESULT_OK, intent)
        mainListener.hideFragment()
    }

    override fun onClick(v: View) {
        when (v.id) {

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
//                presenterOne.loadOfficialTestsByQUery(query)

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                findFromList(newText)

                if (!finalSearchView.isIconified) {
                    finalSearchView.isIconified = true
                }
                searchItem.collapseActionView()
                return false
            }
        })

    }

    private fun findFromList(query: String) {
        val pattern: Pattern = Pattern.compile("${query.toLowerCase()}.*")
        val list: MutableList<Skill> = java.util.ArrayList()
        for(skill in skills) {
            if(pattern.matcher(skill.name.toLowerCase()).matches()) {
                list.add(skill)
            }
        }
        changeDataSet(list)
    }
}
