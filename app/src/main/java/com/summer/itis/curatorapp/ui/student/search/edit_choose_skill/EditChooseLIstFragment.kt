package com.summer.itis.curatorapp.ui.student.search.edit_choose_skill

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.skill.Skill
import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragment
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationView
import com.summer.itis.curatorapp.ui.skill.skill_list.edit.EditSkillsFragment
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const.OWNER_TYPE
import com.summer.itis.curatorapp.utils.Const.SKILL_KEY
import com.summer.itis.curatorapp.utils.Const.gsonConverter
import io.reactivex.disposables.Disposable
import com.google.gson.reflect.TypeToken
import com.summer.itis.curatorapp.ui.student.search.search_filter.SearchFilterFragment.Companion.EDIT_CHOOSED_SKILLS
import kotlinx.android.synthetic.main.fragment_edit_skills.*
import kotlinx.android.synthetic.main.layout_recycler_list.*
import kotlinx.android.synthetic.main.toolbar_choose.*
import java.util.regex.Pattern


class EditChooseLIstFragment : BaseFragment<EditChosePreseter>(), EditChooseView, View.OnClickListener {

    lateinit var user: Curator
    var type: String = OWNER_TYPE
    lateinit override var mainListener: NavigationView
    private lateinit var adapter: EditChooseAdapter

    var skills: MutableList<Skill> = ArrayList()

    @InjectPresenter
    lateinit var presenter: EditChosePreseter

    private lateinit var skill: Skill

    companion object {

        const val TAG_SKILLS = "TAG_SKILLS"


        fun newInstance(args: Bundle, navigationView: NavigationView): Fragment {
            val fragment = EditChooseLIstFragment()
            fragment.arguments = args
            fragment.mainListener = navigationView
            return fragment
        }

        fun newInstance(navigationView: NavigationView): Fragment {
            val fragment = EditChooseLIstFragment()
            fragment.mainListener = navigationView
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        user = AppHelper.currentCurator
        arguments?.let {
            val founderListType = object : TypeToken<ArrayList<Skill>>() { }.type
            val skillsJson = it.getString(SKILL_KEY)
            skills = gsonConverter.fromJson(skillsJson, founderListType)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_edit_skills, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        setToolbarData()
        initRecycler()
        setListeners()

        if(skills.size != 0) {
            changeDataSet(skills)
        }
    }

    private fun setToolbarData() {
        mainListener.setToolbar(toolbar_choose)
        btn_cancel.visibility = View.VISIBLE
        btn_ok.visibility = View.VISIBLE

    }

    private fun setListeners() {
        btn_cancel.setOnClickListener(this)
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
        adapter = EditChooseAdapter(ArrayList(), this)
        val manager = LinearLayoutManager(activity as Activity)
        rv_list.layoutManager = manager
        rv_list.setEmptyView(tv_empty)
        adapter.attachToRecyclerView(rv_list)
        adapter.setOnItemClickListener(this)
    }

    override fun onItemClick(item: Skill) {
    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.btn_cancel -> backFragment()

            R.id.btn_ok -> saveChanges()

        }
    }

    private fun saveChanges() {
        val listJson = gsonConverter.toJson(skills)
        val intent = Intent()
        intent.putExtra(SKILL_KEY, listJson)
        targetFragment?.onActivityResult(EDIT_CHOOSED_SKILLS, Activity.RESULT_OK, intent)
        mainListener.hideFragment()
//        backFragment()
    }

    private fun setSearchMenuItem() {
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
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
        val list: MutableList<Skill> = java.util.ArrayList()
        for(skill in skills) {
            if(pattern.matcher(skill.name.toLowerCase()).matches()) {
                list.add(skill)
            }
        }
        changeDataSet(list)
    }

    override fun remove(pos: Int) {
        skills.removeAt(pos)
        changeDataSet(skills)
    }
}
