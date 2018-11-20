package com.summer.itis.curatorapp.ui.theme.theme_list.tab_fragment.my_theme_list

import android.view.ViewGroup
import com.summer.itis.curatorapp.model.theme.Theme
import com.summer.itis.curatorapp.ui.base.base_first.BaseAdapter
import com.summer.itis.curatorapp.ui.skill.skill_list.edit.EditSkillsView

class ThemeAdapter(items: MutableList<Theme>, private val fakeListener: MyThemeListView) : BaseAdapter<Theme, ThemeItemHolder>(items) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThemeItemHolder {
        return ThemeItemHolder.create(parent, fakeListener)
    }

    override fun onBindViewHolder(holder: ThemeItemHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val item = getItem(position)
        holder.bind(item)
    }
}