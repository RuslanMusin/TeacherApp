package com.summer.itis.curatorapp.ui.theme.edit_suggestion

import android.content.Intent
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragView

interface EditSuggestionView: BaseFragView {

    fun returnEditResult(intent: Intent?)
}