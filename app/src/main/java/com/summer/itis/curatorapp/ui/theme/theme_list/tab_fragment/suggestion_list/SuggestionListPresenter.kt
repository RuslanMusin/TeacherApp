package com.summer.itis.curatorapp.ui.theme.theme_list.tab_fragment.suggestion_list

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.theme.SuggestionTheme
import com.summer.itis.curatorapp.model.work.Work
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const.ACCEPTED_BOTH
import com.summer.itis.curatorapp.utils.Const.CHANGED_STUDENT
import com.summer.itis.curatorapp.utils.Const.IN_PROGRESS_CURATOR
import com.summer.itis.curatorapp.utils.Const.REJECTED_CURATOR
import com.summer.itis.curatorapp.utils.Const.REJECTED_STUDENT
import io.reactivex.disposables.CompositeDisposable
import java.util.*

@InjectViewState
class SuggestionListPresenter(): BaseFragPresenter<SuggestionListView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun setFakeResponse(sug: SuggestionTheme, action: String, context: Context) {
        when(action) {

            context.getString(R.string.accept_theme) -> {
                val work = Work()
                work.id = sug.id
                work.dateStart = Date()
                sug.theme?.let {
                    it.student = sug.student
                    it.curator = sug.curator
                    work.theme = it
                }
                AppHelper.currentCurator.works.add(0, work)
                val iterator = AppHelper.currentCurator.suggestions.iterator()
                for(suggestion in iterator) {
                    if(sug.id.equals(suggestion.id)) {
                        suggestion.status = ACCEPTED_BOTH
                    }
                    else if(suggestion.theme?.id.equals(sug.theme?.id)) {
                        suggestion.status = REJECTED_CURATOR
                    }
                }
                for(theme in AppHelper.currentCurator.themes) {
                    if(theme.id.equals(sug.theme?.id)) {
                        theme.dateAcceptance = Date()
                        theme.student = sug.student
                    }
                }
                viewState.changeDataSet(AppHelper.currentCurator.suggestions)
                viewState.saveCuratorState()
            }

            context.getString(R.string.reject_theme) -> {
                for(suggestion in AppHelper.currentCurator.suggestions) {
                    if(sug.id.equals(suggestion.id)) {
                        suggestion.status = REJECTED_STUDENT
                    }
                }
                viewState.changeDataSet(AppHelper.currentCurator.suggestions)
                viewState.saveCuratorState()
            }

            context.getString(R.string.to_revision) -> {
                for(suggestion in AppHelper.currentCurator.suggestions) {
                    if(sug.id.equals(suggestion.id)) {
                        suggestion.status = IN_PROGRESS_CURATOR
                    }
                }
                viewState.changeDataSet(AppHelper.currentCurator.suggestions)
                viewState.saveCuratorState()
            }

            context.getString(R.string.save_changes) -> {
                changeSuggestionStatus(sug.id, CHANGED_STUDENT)
            }

        }
    }

    fun changeSuggestionStatus(id: String, status: String) {
        for (sug in AppHelper.currentCurator.suggestions) {
            if (sug.id.equals(id)) {
                sug.status = status
            }
        }
        viewState.changeDataSet(AppHelper.currentCurator.suggestions)
        viewState.saveCuratorState()
    }


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