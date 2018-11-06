package com.summer.itis.curatorapp.ui.base.base_first.activity

import com.arellomobile.mvp.MvpView

interface BasicFunctional: MvpView {

    fun showProgressDialog(message: String)

    fun showProgressDialog(messageId: Int)

    fun hideProgressDialog()

    fun showSnackBar(message: String)

    fun showSnackBar(messageId: Int)
}