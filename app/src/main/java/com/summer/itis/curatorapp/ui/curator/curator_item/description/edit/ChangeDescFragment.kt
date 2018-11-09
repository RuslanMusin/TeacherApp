package com.summer.itis.curatorapp.ui.curator.curator_item.description.edit

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragment
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationView
import com.summer.itis.curatorapp.ui.curator.curator_item.description.view.DescriptionFragment.Companion.EDIT_DESC
import com.summer.itis.curatorapp.utils.Const.DESC_KEY
import com.summer.itis.curatorapp.utils.Const.ID_KEY
import com.summer.itis.curatorapp.utils.Const.TYPE
import kotlinx.android.synthetic.main.fragment_change_description.*
import kotlinx.android.synthetic.main.toolbar_edit.*

class ChangeDescFragment : BaseFragment<ChangeDescPresenter>(), ChangeDescView, View.OnClickListener {

    lateinit var description: String
    lateinit var type: String
    var id: String? = null
    override lateinit var mainListener: NavigationView

    @InjectPresenter
    lateinit var presenter: ChangeDescPresenter

    companion object {

        const val TAG_CURATOR = "TAG_CURATOR"

        fun newInstance(args: Bundle, navigationView: NavigationView): Fragment {
            val fragment = ChangeDescFragment()
            fragment.arguments = args
            fragment.mainListener = navigationView
            return fragment
        }

        fun newInstance(navigationView: NavigationView): Fragment {
            val fragment = ChangeDescFragment()
            fragment.mainListener = navigationView
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            description = it.getString(DESC_KEY)
            type = it.getString(TYPE)
            id = it.getString(ID_KEY)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_change_description, container, false)
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
        toolbar_edit.title = getString(R.string.desc)

    }

    private fun setListeners() {
        btn_ok.setOnClickListener(this)
        btn_back.setOnClickListener(this)
    }

    private fun setUserData() {
        et_description.setText(description)

    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.btn_ok -> changeData()

            R.id.btn_back -> backFragment()
        }
    }

    private fun changeData() {
        description = et_description.text.toString()
        val intent = Intent()
        intent.putExtra(DESC_KEY, description)
        targetFragment?.onActivityResult(EDIT_DESC, RESULT_OK, intent)
        presenter.saveDescription(description, type, id)
//        this.activity?.let { AppHelper.saveCurrentState(user, it) }
//        backFragment()
        mainListener.hideFragment()
    }
}
