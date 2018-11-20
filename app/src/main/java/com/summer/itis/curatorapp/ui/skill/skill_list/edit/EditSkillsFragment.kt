package com.summer.itis.curatorapp.ui.skill.skill_list.edit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.*
import com.arellomobile.mvp.presenter.InjectPresenter
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.skill.Skill
import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragment
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationBaseActivity
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationBaseActivity.Companion.SHOW_PROFILE
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationView
import com.summer.itis.curatorapp.ui.skill.skill_list.edit.add_skill.AddSkillFragment
import com.summer.itis.curatorapp.ui.skill.skill_list.view.SkillListFragment.Companion.EDIT_SKILLS
import com.summer.itis.curatorapp.ui.student.search.choose_skill_main.ChooseSkillFragment
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const
import com.summer.itis.curatorapp.utils.Const.ADD_SKILL
import com.summer.itis.curatorapp.utils.Const.OWNER_TYPE
import com.summer.itis.curatorapp.utils.Const.SKILL_KEY
import com.summer.itis.curatorapp.utils.Const.TAG_LOG
import com.summer.itis.curatorapp.utils.Const.gsonConverter
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_change_skills.*
import kotlinx.android.synthetic.main.layout_recycler_list.*
import kotlinx.android.synthetic.main.toolbar_add_done.*
import java.util.regex.Pattern

class EditSkillsFragment : BaseFragment<EditSkillsPresenter>(), EditSkillsView, View.OnClickListener {

    lateinit var user: Curator
    var type: String = OWNER_TYPE
    lateinit override var mainListener: NavigationView
    private lateinit var adapter: EditSkillAdapter

    var skills: MutableList<Skill> = ArrayList()

    @InjectPresenter
    lateinit var presenter: EditSkillsPresenter

    private lateinit var skill: Skill

    companion object {

        const val TAG_SKILLS = "TAG_SKILLS"

        fun newInstance(args: Bundle, navigationView: NavigationView): Fragment {
            val fragment = EditSkillsFragment()
            fragment.arguments = args
            fragment.mainListener = navigationView
            return fragment
        }

        fun newInstance(navigationView: NavigationView): Fragment {
            val fragment = EditSkillsFragment()
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
        val view = inflater.inflate(R.layout.fragment_change_skills, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        loadSkills()
    }

    private fun loadSkills() {
//        presenterOne.loadSkills(AppHelper.currentCurator.id)
        if(user.skills.size == 0) {
            skills = ArrayList()
            for (i in 1..10) {
                val skill = Skill()
                skill.id = "$i"
                if(i / 2 == 0) {
                    skill.level = getString(R.string.low_level)
                    skill.name = "Machine Learning $i"
                } else {
                    skill.level = getString(R.string.high_level)
                    skill.name = "Android $i"
                }
                skills.add(skill)
            }

        } else {
            skills = user.skills
        }
        changeDataSet(skills)

    }

    private fun initViews() {
        setToolbarData()
        initRecycler()
        setListeners()
    }

    private fun setToolbarData() {
        mainListener.setToolbar(toolbar_add_done)
        btn_back.visibility = View.VISIBLE
        btn_add.visibility = View.VISIBLE
        btn_ok.visibility = View.VISIBLE

    }

    private fun setListeners() {
        btn_add.setOnClickListener(this)
        btn_back.setOnClickListener(this)
        btn_ok.setOnClickListener(this)

        setSearchMenuItem()
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
        adapter = EditSkillAdapter(ArrayList(), this)
        val manager = LinearLayoutManager(activity as Activity)
        rv_list.layoutManager = manager
        rv_list.setEmptyView(tv_empty)
        adapter.attachToRecyclerView(rv_list)
        adapter.setOnItemClickListener(this)
    }

    override fun onItemClick(item: Skill) {
//        TestActivity.start(this, item)
    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.btn_add -> addSkill()

            R.id.btn_back -> backFragment()

            R.id.btn_ok -> saveChanges()

        }
    }

    private fun saveChanges() {
        user.skills = skills
        val listJson = gsonConverter.toJson(user.skills)
        val intent = Intent()
        intent.putExtra(SKILL_KEY, listJson)
        targetFragment?.onActivityResult(EDIT_SKILLS, Activity.RESULT_OK, intent)
        mainListener.hideFragment()
//        backFragment()
    }

    private fun addSkill() {
       /* val fragment = AddSkillFragment.newInstance(mainListener)
        fragment.setTargetFragment(this, ADD_SKILL)
        mainListener.showFragment(SHOW_PROFILE, this, fragment)*/
        val fragment = ChooseSkillFragment.newInstance(mainListener)
        fragment.setTargetFragment(this, ADD_SKILL)
        mainListener.showFragment(SHOW_PROFILE, this, fragment)
    }

   /* override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.search_menu, menu)
        menu?.let { setSearchMenuItem(it) }
        super.onCreateOptionsMenu(menu, inflater)
    }
*/
    private fun setSearchMenuItem() {
       /*val searchItem = menu.findItem(R.id.action_search)

        val searchView: SearchView = searchItem.actionView as SearchView
        val finalSearchView = searchView*/
       search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

           override fun onQueryTextSubmit(query: String): Boolean {
//                presenterOne.loadOfficialTestsByQUery(query)

               return false
           }

           override fun onQueryTextChange(newText: String): Boolean {
               findFromList(newText)
               /*if (!search.isIconified) {
                   search.isIconified = true
               }*/
//               searchItem.collapseActionView()
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

    override fun onActivityResult(reqCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(reqCode, resultCode, data)

        if (reqCode == ADD_SKILL && resultCode == Activity.RESULT_OK) {
            Log.d(TAG_LOG, "getResult")
            data?.getStringExtra(SKILL_KEY)?.let {
                skill = gsonConverter.fromJson(it, Skill::class.java)
                var bool = true
                for(skillItem in skills) {
                    if(skillItem.id == skill.id) {
                        bool = false
                    }
                }
                if(bool == true) {
                    Log.d(TAG_SKILLS, "skill added")
                    skills.add(0, skill)
                    user.skills = skills
                    this.activity?.let { it1 -> AppHelper.saveCurrentState(user, it1) }
                    changeDataSet(skills)
                }
            }
        }
    }

    override fun remove(pos: Int) {
        skills.removeAt(pos)
        changeDataSet(skills)
    }

    override fun chooseLevel(pos: Int, level: String) {
        skills[pos].level = level
    }
}
