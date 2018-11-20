package com.summer.itis.curatorapp.ui.theme.suggestion_item

import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.model.theme.SuggestionTheme
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.ui.comment.CommentPresenter
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const
import com.summer.itis.curatorapp.utils.Const.CHANGED_CURATOR
import com.summer.itis.curatorapp.utils.Const.IN_PROGRESS_CURATOR
import com.summer.itis.curatorapp.utils.Const.IN_PROGRESS_STUDENT
import com.summer.itis.curatorapp.utils.Const.WAITING_CURATOR
import com.summer.itis.curatorapp.utils.Const.WAITING_STUDENT

@InjectViewState
class  SuggestionPresenter(): CommentPresenter<SuggestionView>() {

    fun rejectCurator(suggestionTheme: SuggestionTheme) {
        for (sug in AppHelper.currentCurator.suggestions) {
            if (sug.id.equals(suggestionTheme.id)) {
                sug.status = Const.REJECTED_CURATOR
                viewState.setStatus(sug.status)

            }
        }
    }

    fun revisionTheme (suggestionTheme: SuggestionTheme) {
        when (suggestionTheme.status) {

            IN_PROGRESS_CURATOR -> changeSuggestionStatus(suggestionTheme.id, CHANGED_CURATOR)

            WAITING_CURATOR -> changeSuggestionStatus(suggestionTheme.id, IN_PROGRESS_STUDENT)
        }
    }

    fun changeSuggestionStatus(id: String, status: String) {
        for (sug in AppHelper.currentCurator.suggestions) {
            if (sug.id.equals(id)) {
                sug.status = status
                viewState.setStatus(status)
            }
        }
    }

}