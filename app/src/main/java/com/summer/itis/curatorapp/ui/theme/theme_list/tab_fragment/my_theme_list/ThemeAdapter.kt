package com.summer.itis.curatorapp.ui.theme.theme_list.tab_fragment.my_theme_list

import android.view.ViewGroup
import com.summer.itis.curatorapp.model.theme.Theme
import com.summer.itis.curatorapp.ui.base.base_first.BaseAdapter

class ThemeAdapter(items: MutableList<Theme>) : BaseAdapter<Theme, ThemeItemHolder>(items) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThemeItemHolder {
        return ThemeItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ThemeItemHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val item = getItem(position)
        holder.bind(item)
    }
}