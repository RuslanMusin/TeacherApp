package com.summer.itis.curatorapp.ui.theme.edit_theme

import android.content.Intent
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragView

interface EditThemeView: BaseFragView {

    fun returnEditResult(intent: Intent?)
}