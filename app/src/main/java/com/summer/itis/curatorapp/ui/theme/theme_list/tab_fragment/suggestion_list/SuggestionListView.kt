package com.summer.itis.curatorapp.ui.theme.theme_list.tab_fragment.suggestion_list

import com.summer.itis.curatorapp.model.theme.SuggestionTheme
import com.summer.itis.curatorapp.ui.base.base_first.BaseRecyclerView
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragView
import com.summer.itis.curatorapp.ui.base.base_custom.SearchListener

interface SuggestionListView: BaseFragView, BaseRecyclerView<SuggestionTheme>, SearchListener {

    fun chooseUserFakeAction(pos: Int)
}