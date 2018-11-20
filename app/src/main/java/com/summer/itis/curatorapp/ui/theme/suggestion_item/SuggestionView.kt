package com.summer.itis.curatorapp.ui.theme.suggestion_item

import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragView
import com.summer.itis.curatorapp.ui.comment.CommentView

interface SuggestionView: CommentView {

    fun setStatus(status: String)

}