package com.summer.itis.curatorapp.ui.base.base_first.fragment

import com.arellomobile.mvp.MvpView
import com.summer.itis.curatorapp.model.user.Curator

interface BaseFragView: MvpView {

    fun saveCuratorState()
}