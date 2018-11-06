package com.summer.itis.curatorapp.ui.theme.theme_list.tab_fragment.my_theme_list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.theme.Theme
import kotlinx.android.synthetic.main.item_theme.view.*

class ThemeItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: Theme) {
        itemView.tv_theme.text = item.title
        itemView.tv_subject.text = item.subject.name
        itemView.tv_curator.text = item.curator?.name
    }


    companion object {

        fun create(parent: ViewGroup): ThemeItemHolder {
            val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_theme, parent, false);
            val holder = ThemeItemHolder(view)
            return holder
        }
    }
}
