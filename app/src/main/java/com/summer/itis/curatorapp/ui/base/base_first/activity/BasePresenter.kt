package com.summer.itis.curatorapp.ui.base.base_first.activity

import com.arellomobile.mvp.MvpPresenter

open class BasePresenter<View: BaseActView>: MvpPresenter<View>() {

    var isStopped: Boolean = false

  /*  fun changeUserStatus(status: String) {
        userRepository.changeJustUserStatus(status).subscribe()
    }

    fun checkUserConnection(checkIt: () -> (Unit)) {
        userRepository.checkUserConnection(checkIt)
    }*/

}