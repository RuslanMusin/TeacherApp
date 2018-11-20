package com.summer.itis.curatorapp.ui.theme.edit_theme

import android.content.Context
import android.content.Intent
import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.theme.SuggestionTheme
import com.summer.itis.curatorapp.model.theme.Theme
import com.summer.itis.curatorapp.model.theme.ThemeProgress
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.ui.theme.add_theme.AddThemeView
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const.ADD_THEME_TYPE
import com.summer.itis.curatorapp.utils.Const.CURATOR_TYPE
import com.summer.itis.curatorapp.utils.Const.SUGGESTION_TYPE
import com.summer.itis.curatorapp.utils.Const.THEME_KEY
import com.summer.itis.curatorapp.utils.Const.THEME_TYPE
import com.summer.itis.curatorapp.utils.Const.gsonConverter
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class EditThemePresenter(): BaseFragPresenter<EditThemeView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun saveThemeEdit(themeNew: Theme) {
        val iterator = AppHelper.currentCurator.themes.iterator()
        for((i, theme) in iterator.withIndex()) {
            if(theme.id.equals(themeNew.id)) {
                AppHelper.currentCurator.themes[i] = themeNew

                val suggestionIterator = AppHelper.currentCurator.suggestions.iterator()
                for(suggestion  in suggestionIterator) {
                    if(suggestion.theme?.id.equals(themeNew.id)) {
                        suggestion.theme = themeNew
                        suggestion.themeProgress?.title = themeNew.title
                        suggestion.themeProgress?.description = themeNew.description
                        suggestion.themeProgress?.skills = themeNew.skills
                    }
                }
                viewState.saveCuratorState()

                val intent = Intent()
                intent.putExtra(THEME_KEY , gsonConverter.toJson(themeNew))
                viewState.returnEditResult(intent)

                break
            }
        }

    }

}