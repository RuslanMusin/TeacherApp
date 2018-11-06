package com.summer.itis.curatorapp.ui.base.navigation_base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.ui.base.base_first.activity.BaseActView

interface NavigationView: BaseActView {

    fun supportActionBar(toolbar: Toolbar)

    fun loadFragment(fragment: Fragment)

    fun pushFragments(tag: String, fragment: Fragment, shouldAdd: Boolean)

    fun showFragment(lastFragment: Fragment, fragment: Fragment)

    fun hideFragment(fragment: Fragment)

    fun onBackPressed()

    fun setResultAndBack(args: Bundle)

    fun openFragmentForEdit(currentFragment: Fragment, editfragment: Fragment)

    fun hideBottomNavigation()

    fun showBottomNavigation()

    fun changeWindowsSoftInputMode(mode: Int)

}