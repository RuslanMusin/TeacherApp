package com.summer.itis.curatorapp.ui.skill.skill_list.edit

import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.repository.RepositoryProvider.Companion.skillRepository
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.ui.skill.skill_list.view.SkillListView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer

@InjectViewState
class EditSkillsPresenter(): BaseFragPresenter<EditSkillsView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()


    fun loadSkills(userId: String) {
        val disposable = skillRepository
                .findMySkills(userId)
                .doOnSubscribe(Consumer<Disposable> { viewState.showLoading(it) })
                .doAfterTerminate(Action { viewState.hideLoading() })
                .subscribe ({ viewState.changeDataSet(it) }, { viewState.handleError(it) })
        compositeDisposable.add(disposable)
    }

}