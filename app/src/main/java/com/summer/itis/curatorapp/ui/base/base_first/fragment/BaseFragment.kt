package com.summer.itis.curatorapp.ui.base.base_first.fragment

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.arellomobile.mvp.MvpAppCompatFragment
import com.summer.itis.curatorapp.R.string.curator
import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.model.user.Person
import com.summer.itis.curatorapp.ui.base.base_first.activity.BasePresenter
import com.summer.itis.curatorapp.ui.base.base_first.activity.BaseActView
import com.summer.itis.curatorapp.ui.base.base_first.activity.BaseActivity
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationView
import com.summer.itis.curatorapp.ui.curator.curator_item.edit.EditCuratorFragment.Companion.RESULT_CANCELED
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const
import com.summer.itis.curatorapp.utils.Const.CURATOR_TYPE
import com.summer.itis.curatorapp.utils.Const.PERSON_TYPE
import com.summer.itis.curatorapp.utils.Const.TAG_LOG
import com.summer.itis.curatorapp.utils.Const.USER_KEY
import com.summer.itis.curatorapp.utils.Const.gsonConverter

//СТАНДАРТНЫЙ БАЗОВЫЙ АКТИВИТИ ДЛЯ ТЕХ,КОМУ НЕ НУЖНА НАВИГАЦИЯ, НО ЕСТЬ СТРЕЛКА НАЗАД И ПРОГРЕСС БАР.
abstract class BaseFragment<Presenter: BaseFragPresenter<*>> : MvpAppCompatFragment(), BaseFragView {

    abstract var mainListener: NavigationView

    protected fun backFragment() {
        mainListener.onBackPressed()
       /* fragmentManager?.popBackStackImmediate()
        if(fragmentManager == null) {
            Log.i(TAG_LOG, "manager - null")
        }*/
    }

    protected fun onCanceledAction(requestCode: Int, intent: Intent?) {
        targetFragment?.onActivityResult(requestCode, RESULT_CANCELED, intent)
    }

    protected fun argUser(curator: Person, type: String): Bundle {
        val args: Bundle = Bundle()
        val userJson = Const.gsonConverter.toJson(curator)
        args.putString(Const.USER_KEY, userJson)
        args.putString(PERSON_TYPE, type)
        return args
    }

    protected fun argUser(curator: Person): Bundle {
       return argUser(curator, CURATOR_TYPE)
    }

    override fun saveCuratorState() {
        context?.let {
            val curator = AppHelper.currentCurator
            val editor = it.getSharedPreferences(curator.email, MODE_PRIVATE).edit()
            val curatorJson = gsonConverter.toJson(curator)
            editor.putString(USER_KEY, curatorJson)
            editor.apply()
        }
    }

}