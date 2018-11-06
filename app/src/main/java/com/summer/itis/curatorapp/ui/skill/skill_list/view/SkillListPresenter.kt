package com.summer.itis.curatorapp.ui.skill.skill_list.view

import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.repository.RepositoryProvider.Companion.skillRepository
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer

@InjectViewState
class SkillListPresenter(): BaseFragPresenter<SkillListView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()


    fun loadSkills(userId: String, type: String) {
     /* val disposable =
              findSkillsByType(userId, type)

              .doOnSubscribe(Consumer<Disposable> { viewState.showLoading(it) })
              .doAfterTerminate(Action { viewState.hideLoading() })
              .subscribe ({ viewState.changeDataSet(it) }, { viewState.handleError(it) })
        compositeDisposable.add(disposable)*/
    }

    fun findSkillsByType(userId: String, type: String) {
       /* if(type.equals(CURATOR_TYPE)) {
            skillRepository
                    .findMySkills(userId)
        } else {

        }*/
    }

}