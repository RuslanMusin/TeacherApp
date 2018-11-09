package com.summer.itis.curatorapp.ui.base.navigation_base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import com.summer.itis.curatorapp.ui.base.base_first.activity.BaseActView

interface NavigationView: BaseActView {

    fun supportActionBar(toolbar: Toolbar)

    fun pushFragments(tag: String, fragment: Fragment, shouldAdd: Boolean)

    fun showFragment(tabId: String, lastFragment: Fragment, fragment: Fragment)

    fun hideFragment()

    fun onBackPressed()

    fun hideBottomNavigation()

    fun showBottomNavigation()

    fun changeWindowsSoftInputMode(mode: Int)

}