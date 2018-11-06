package com.summer.itis.curatorapp.ui.theme.theme_list.tab_fragment.suggestion_list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.theme.SuggestionTheme
import com.summer.itis.curatorapp.ui.theme.theme_list.tab_fragment.my_theme_list.ThemeItemHolder
import kotlinx.android.synthetic.main.item_theme.view.*

class SuggestionItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: SuggestionTheme) {
        itemView.tv_theme.text = item.themeProgress?.title
        itemView.tv_subject.text = item.themeProgress?.subject?.name
        itemView.tv_curator.text = item.curator?.printFullName()
    }


    companion object {

        fun create(parent: ViewGroup): SuggestionItemHolder {
            val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_theme, parent, false);
            val holder = SuggestionItemHolder(view)
            return holder
        }
    }
}
