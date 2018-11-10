package com.summer.itis.curatorapp.ui.theme.edit_suggestion

import android.content.Intent
import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.model.theme.ThemeProgress
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const.THEME_KEY
import com.summer.itis.curatorapp.utils.Const.gsonConverter
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class EditSuggestionPresenter(): BaseFragPresenter<EditSuggestionView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun saveSuggestionEdit(themeProgress: ThemeProgress) {
        for(suggestion in AppHelper.currentCurator.suggestions) {
            if(suggestion.themeProgress?.id.equals(themeProgress.id)) {
                suggestion.themeProgress = themeProgress
                viewState.saveCuratorState()

                val intent = Intent()
                intent.putExtra(THEME_KEY , gsonConverter.toJson(themeProgress))
                viewState.returnEditResult(intent)

                break
            }
        }
    }

}