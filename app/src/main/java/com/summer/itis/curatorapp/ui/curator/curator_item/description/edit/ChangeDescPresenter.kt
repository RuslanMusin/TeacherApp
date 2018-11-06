package com.summer.itis.curatorapp.ui.curator.curator_item.description.edit

import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const.CURATOR_TYPE
import com.summer.itis.curatorapp.utils.Const.SUGGESTION_TYPE
import com.summer.itis.curatorapp.utils.Const.THEME_TYPE

@InjectViewState
class ChangeDescPresenter(): BaseFragPresenter<ChangeDescView>() {

    fun saveDescription(description: String, type: String, id: String?) {

        when(type) {

            CURATOR_TYPE -> saveCuratorDesc(description)

            SUGGESTION_TYPE -> id?.let { saveSuggestionDesc(description, it) }

            THEME_TYPE -> id?.let { saveThemeDesc(description, it) }
        }
    }

    private fun saveThemeDesc(description: String, id: String) {
        val themes = AppHelper.currentCurator.themes
        for(theme in themes) {
            if(theme.id.equals(id)) {
                theme.description = description
                viewState.saveCuratorState()
            }
        }
    }

    private fun saveSuggestionDesc(description: String, id: String) {
        val suggestions = AppHelper.currentCurator.suggestions
        for(suggestionTheme in suggestions) {
            if(suggestionTheme.id.equals(id)) {
                suggestionTheme.themeProgress?.description = description
                viewState.saveCuratorState()
            }
        }
    }

    private fun saveCuratorDesc(description: String) {
        AppHelper.currentCurator.description = description
        viewState.saveCuratorState()
    }
}