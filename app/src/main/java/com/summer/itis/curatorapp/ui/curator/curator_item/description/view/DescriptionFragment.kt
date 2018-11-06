package com.summer.itis.curatorapp.ui.curator.curator_item.description.view

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
import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.model.user.Person
import com.summer.itis.curatorapp.model.user.Student
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragment
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationBaseActivity
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationBaseActivity.Companion.TAB_PROFILE
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationBaseActivity.Companion.TAB_STUDENTS
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationBaseActivity.Companion.TAB_THEMES
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationView
import com.summer.itis.curatorapp.ui.curator.curator_item.description.edit.ChangeDescFragment
import com.summer.itis.curatorapp.ui.curator.curator_item.view.CuratorFragment.Companion.TAG_CURATOR
import com.summer.itis.curatorapp.ui.skill.skill_list.view.SkillListPresenter
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const
import com.summer.itis.curatorapp.utils.Const.CURATOR_TYPE
import com.summer.itis.curatorapp.utils.Const.DESC_KEY
import com.summer.itis.curatorapp.utils.Const.ID_KEY
import com.summer.itis.curatorapp.utils.Const.OWNER_TYPE
import com.summer.itis.curatorapp.utils.Const.PERSON_TYPE
import com.summer.itis.curatorapp.utils.Const.STUDENT_TYPE
import com.summer.itis.curatorapp.utils.Const.SUGGESTION_TYPE
import com.summer.itis.curatorapp.utils.Const.THEME_TYPE
import com.summer.itis.curatorapp.utils.Const.TYPE
import com.summer.itis.curatorapp.utils.Const.WATCHER_TYPE
import kotlinx.android.synthetic.main.fragment_description.*
import kotlinx.android.synthetic.main.toolbar_edit.*

class DescriptionFragment : BaseFragment<DescriptionPresenter>(), DescriptionView, View.OnClickListener {

    lateinit var type: String
    var isOwner: Boolean = false
    lateinit var description: String
    var id: String? = null
    lateinit override var mainListener: NavigationView

    @InjectPresenter
    lateinit var presenter: DescriptionPresenter

    companion object {

        const val TAG_SKILLS = "TAG_SKILLS"

        const val EDIT_DESC = 1

        fun newInstance(args: Bundle, navigationView: NavigationView): Fragment {
            val fragment = DescriptionFragment()
            fragment.arguments = args
            fragment.mainListener = navigationView
            return fragment
        }

        fun newInstance(navigationView: NavigationView): Fragment {
            val fragment = DescriptionFragment()
            fragment.mainListener = navigationView
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            description = it.getString(Const.DESC_KEY)
            val userId: String? = it.getString(Const.USER_ID)
            type = it.getString(TYPE)
            id = it.getString(ID_KEY)
            if(userId != null && AppHelper.currentCurator.id.equals(userId)) {
                isOwner = true
            }

         /*   personType = it.getString(PERSON_TYPE)
            if(personType.equals(STUDENT_TYPE)) {
                type = WATCHER_TYPE
                user = Const.gsonConverter.fromJson(it.getString(Const.USER_KEY), Student::class.java)
            } else {
                user = Const.gsonConverter.fromJson(it.getString(Const.USER_KEY), Curator::class.java)
                if(user.id != AppHelper.currentCurator.id) {
                    type = WATCHER_TYPE
                }
            }*/
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_description, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    fun initViews() {
        findViews()
        setToolbarData()
        setListeners()
    }

    private fun setToolbarData() {
        mainListener.setToolbar(toolbar_edit)
        if(!isOwner || type.equals(THEME_TYPE) || type.equals(SUGGESTION_TYPE)) {
            btn_edit.visibility = View.GONE
        }
        toolbar_edit.title = getString(R.string.desc)
        btn_back.visibility = View.VISIBLE

    }

    private fun setListeners() {
        btn_edit.setOnClickListener(this)
        btn_back.setOnClickListener(this)
    }

    private fun findViews() {
        tv_desc_name.text = description
    }


    override fun onClick(v: View) {
        when (v.id) {

            R.id.btn_edit -> editDesc()

            R.id.btn_back -> backFragment()

        }
    }

    private fun editDesc() {
        val args = Bundle()
        args.putString(DESC_KEY, description)
        args.putString(TYPE, type)
        id?.let { args.putString(ID_KEY, id) }
        val fragment = ChangeDescFragment.newInstance(args, mainListener)
        fragment.setTargetFragment(this, EDIT_DESC)
        mainListener.showFragment(this, fragment)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when(requestCode) {

                EDIT_DESC -> {
                    tv_desc_name.text = data?.getStringExtra(DESC_KEY)
                }
            }
        }
    }

    private fun getTab(): String {
        if(type.equals(SUGGESTION_TYPE) || type.equals(THEME_TYPE)) {
            return TAB_THEMES
        }
        if(type.equals(STUDENT_TYPE)) {
            return TAB_STUDENTS
        }
        if(type.equals(CURATOR_TYPE)) {
            return TAB_PROFILE
        }
        return TAB_PROFILE
    }

}
