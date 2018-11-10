package com.summer.itis.curatorapp.ui.theme.add_theme

import android.content.Context
import android.content.Intent
import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.theme.SuggestionTheme
import com.summer.itis.curatorapp.model.theme.Theme
import com.summer.itis.curatorapp.model.theme.ThemeProgress
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const.ADD_THEME_TYPE
import com.summer.itis.curatorapp.utils.Const.CURATOR_TYPE
import com.summer.itis.curatorapp.utils.Const.SUGGESTION_TYPE
import com.summer.itis.curatorapp.utils.Const.THEME_KEY
import com.summer.itis.curatorapp.utils.Const.THEME_TYPE
import com.summer.itis.curatorapp.utils.Const.gsonConverter
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class AddThemePresenter(): BaseFragPresenter<AddThemeView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun saveChanges(theme: Theme, type: String, context: Context) {
        when(type) {
            ADD_THEME_TYPE -> addTheme(theme,context)

            THEME_TYPE -> saveThemeEdit(theme)

            SUGGESTION_TYPE -> saveSuggestionEdit(theme,context)
        }
    }

    fun saveSuggestionEdit(theme: Theme, context: Context) {
        for(suggestion in AppHelper.currentCurator.suggestions) {
            if(suggestion.themeId.equals(theme.id)) {
                val themeProgress = suggestion.themeProgress
                themeProgress?.title = theme.title
                themeProgress?.description = theme.description
                themeProgress?.subject = theme.subject
                AppHelper.saveCurrentState(AppHelper.currentCurator, context)

                val intent = Intent()
                intent.putExtra(THEME_KEY , gsonConverter.toJson(themeProgress))
                viewState.getResultAfterEdit(true, intent)
            }
        }
    }

    fun saveThemeEdit(themeNew: Theme) {
        val iterator = AppHelper.currentCurator.themes.iterator()
        for((i, theme) in iterator.withIndex()) {
            if(theme.id.equals(themeNew.id)) {
                AppHelper.currentCurator.themes[i] = themeNew
                viewState.saveCuratorState()

                val intent = Intent()
                intent.putExtra(THEME_KEY , gsonConverter.toJson(themeNew))
                viewState.getResultAfterEdit(true, intent)
            }
        }

    }

    fun addTheme(theme: Theme, context: Context) {
        if(theme.student != null) {
            val suggestionTheme = SuggestionTheme()
            suggestionTheme.id = theme.id
            suggestionTheme.theme = theme
            suggestionTheme.status = context.getString(R.string.status_new)
            suggestionTheme.curator = theme.curator
            suggestionTheme.student = theme.student
            suggestionTheme.type = CURATOR_TYPE

            val themeProgress = ThemeProgress()
            themeProgress.title = theme.title
            themeProgress.description = theme.description
            suggestionTheme.themeProgress = themeProgress

            theme.student = null

            AppHelper.currentCurator.suggestions.add(suggestionTheme)
        }


        AppHelper.currentCurator.themes.add(theme)
        AppHelper.saveCurrentState(AppHelper.currentCurator, context)
        viewState.getResultAfterEdit(false, null)
    }

}