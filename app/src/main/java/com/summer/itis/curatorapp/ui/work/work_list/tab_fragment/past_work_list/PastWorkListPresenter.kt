package com.summer.itis.curatorapp.ui.work.work_list.tab_fragment.past_work_list

import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.ui.theme.theme_list.tab_fragment.my_theme_list.MyThemeListView
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class PastWorkListPresenter(): BaseFragPresenter<PastWorkListView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

}