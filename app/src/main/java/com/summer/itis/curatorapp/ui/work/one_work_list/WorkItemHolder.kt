package com.summer.itis.curatorapp.ui.work.one_work_list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.work.Work
import kotlinx.android.synthetic.main.item_work.view.*


class WorkItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: Work) {
        itemView.tv_work.text = item.theme.title
        itemView.tv_subject.text = item.theme.subject.name
    }


    companion object {

        fun create(parent: ViewGroup): WorkItemHolder {
            val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_work, parent, false);
            val holder = WorkItemHolder(view)
            return holder
        }
    }
}
