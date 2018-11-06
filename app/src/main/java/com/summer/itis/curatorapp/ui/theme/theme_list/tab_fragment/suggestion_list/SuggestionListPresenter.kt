package com.summer.itis.curatorapp.ui.theme.theme_list.tab_fragment.suggestion_list

import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.ui.skill.skill_list.view.SkillListView
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class SuggestionListPresenter(): BaseFragPresenter<SuggestionListView>() {

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