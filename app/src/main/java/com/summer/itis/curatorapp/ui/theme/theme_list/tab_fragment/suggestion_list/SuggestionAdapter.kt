package com.summer.itis.curatorapp.ui.theme.theme_list.tab_fragment.suggestion_list

import android.view.ViewGroup
import com.summer.itis.curatorapp.model.theme.SuggestionTheme
import com.summer.itis.curatorapp.ui.base.base_first.BaseAdapter
import com.summer.itis.curatorapp.ui.skill.skill_list.edit.EditSkillsView
import com.summer.itis.curatorapp.ui.theme.theme_list.tab_fragment.my_theme_list.ThemeItemHolder

class SuggestionAdapter(items: MutableList<SuggestionTheme>, private val fakeListener: SuggestionListView) : BaseAdapter<SuggestionTheme, SuggestionItemHolder>(items) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionItemHolder {
        return SuggestionItemHolder.create(parent, fakeListener)
    }

    override fun onBindViewHolder(holder: SuggestionItemHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val item = getItem(position)
        holder.bind(item)
    }
}