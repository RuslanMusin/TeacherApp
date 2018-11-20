package com.summer.itis.curatorapp.ui.theme.theme_item

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.theme.SuggestionTheme
import com.summer.itis.curatorapp.model.theme.Theme
import com.summer.itis.curatorapp.model.theme.ThemeProgress
import com.summer.itis.curatorapp.model.user.Student
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.ui.curator.curator_item.view.CuratorView
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const.CURATOR_TYPE
import com.summer.itis.curatorapp.utils.Const.WAITING_STUDENT
import java.util.*

@InjectViewState
class  ThemePresenter(): BaseFragPresenter<ThemeView>() {

    fun sendSuggestion(theme: Theme, student: Student, context: Context) {
        val suggestionTheme = SuggestionTheme()
        suggestionTheme.id = "${Random().nextInt(24000)}"
        suggestionTheme.type = CURATOR_TYPE
        suggestionTheme.student = student
        suggestionTheme.curator = AppHelper.currentCurator
        suggestionTheme.status = WAITING_STUDENT
        suggestionTheme.theme = theme

        val themeProgress = ThemeProgress()
        themeProgress.title = theme.title
        themeProgress.description = theme.description

        suggestionTheme.themeProgress = themeProgress

        AppHelper.currentCurator.suggestions.add(0, suggestionTheme)
        viewState.saveCuratorState()
    }

}