package com.summer.itis.curatorapp.ui.work.one_work_list

import android.view.ViewGroup
import com.summer.itis.curatorapp.model.work.Work
import com.summer.itis.curatorapp.ui.base.base_first.BaseAdapter


class WorkAdapter(items: MutableList<Work>) : BaseAdapter<Work, WorkItemHolder>(items) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkItemHolder {
        return WorkItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: WorkItemHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val item = getItem(position)
        holder.bind(item)
    }
}