package com.summer.itis.curatorapp.ui.curator.curator_item.edit

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afollestad.materialdialogs.MaterialDialog
import com.arellomobile.mvp.presenter.InjectPresenter
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.R.id.*
import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.repository.RepositoryProvider.Companion.curatorRepository
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragment
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationView
import com.summer.itis.curatorapp.ui.curator.curator_item.view.CuratorFragment
import com.summer.itis.curatorapp.ui.curator.curator_item.view.CuratorFragment.Companion.EDIT_CURATOR
import com.summer.itis.curatorapp.ui.curator.curator_item.view.CuratorPresenter
import com.summer.itis.curatorapp.ui.curator.curator_item.view.CuratorView
import com.summer.itis.curatorapp.ui.login.LoginActivity
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const
import com.summer.itis.curatorapp.utils.Const.OWNER_TYPE
import com.summer.itis.curatorapp.utils.Const.USER_KEY
import com.summer.itis.curatorapp.utils.Const.gsonConverter
import kotlinx.android.synthetic.main.fragment_change_profile.*
import kotlinx.android.synthetic.main.toolbar_edit.*

class EditCuratorFragment : BaseFragment<EditCuratorPresenter>(), EditCuratorView, View.OnClickListener {

    lateinit var user: Curator
    override lateinit var mainListener: NavigationView

    @InjectPresenter
    lateinit var presenter: EditCuratorPresenter

    companion object {

        const val TAG_CURATOR = "TAG_CURATOR"

        const val CURATOR_NAME = "CURATOR_NAME"
        const val CURATOR_LASTNAME = "CURATOR_LASTNAME"
        const val CURATOR_PATRONYMIC = "CURATOR_PATRONYMIC"

        const val RESULT_EDITED = 1
        const val RESULT_CANCELED = 0

        fun newInstance(args: Bundle, navigationView: NavigationView): Fragment {
            val fragment = EditCuratorFragment()
            fragment.arguments = args
            fragment.mainListener = navigationView
            return fragment
        }

        fun newInstance(navigationView: NavigationView): Fragment {
            val fragment = EditCuratorFragment()
            fragment.mainListener = navigationView
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user = AppHelper.currentCurator
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_change_profile, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        super.onViewCreated(view, savedInstanceState)
    }

    fun initViews() {
        setToolbarData()
        setListeners()
        setUserData()
    }

    private fun setToolbarData() {
        mainListener.setToolbar(toolbar_edit)
        btn_edit.visibility = View.GONE
        btn_ok.visibility = View.VISIBLE
        btn_back.visibility = View.VISIBLE
        toolbar_edit.title = "${user.name} ${user.lastname} ${user.patronymic}"

    }

    private fun setListeners() {
        btn_ok.setOnClickListener(this)
        btn_back.setOnClickListener(this)
    }

    private fun setUserData() {
        et_name.setText(user.name)
        et_lastname.setText(user.lastname)
        et_patronymic.setText(user.patronymic)

    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.btn_ok -> changeData()

            R.id.btn_back -> {
                backFragment()
//                onCanceledAction(EDIT_CURATOR, null)
            }
        }
    }

    private fun changeData() {
        user.name = et_name.text.toString()
        user.lastname = et_lastname.text.toString()
        user.patronymic = et_patronymic.text.toString()
        val intent = Intent()
        intent.putExtra(CURATOR_NAME, user.name)
        intent.putExtra(CURATOR_NAME, user.lastname)
        intent.putExtra(CURATOR_NAME, user.patronymic)
        this.activity?.let { AppHelper.saveCurrentState(user, it) }
        targetFragment?.onActivityResult(EDIT_CURATOR, Activity.RESULT_OK, intent)
        backFragment()
    }


}
