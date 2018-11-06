package com.summer.itis.curatorapp.ui.login

import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.ui.base.base_first.activity.BaseActView

interface LoginActView: BaseActView {

    fun showEmailError(hasError: Boolean)

    fun showPasswordError(hasError: Boolean)

    fun showError()

    fun createCookie(email: String, password: String)

    fun goToProfile(curator: Curator)
}