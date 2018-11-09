package com.summer.itis.curatorapp.ui.base.base_first.activity

import android.support.v7.widget.Toolbar


interface BaseActView: BasicFunctional {

    fun setToolbar(toolbar: Toolbar?)

    fun setToolbarTitle(title: String)

    fun setBackArrow(toolbar: Toolbar)

}