package com.summer.itis.curatorapp.ui.comment

import android.view.ViewGroup
import com.summer.itis.curatorapp.model.comment.Comment
import com.summer.itis.curatorapp.ui.base.base_first.BaseAdapter


class CommentAdapter(items: MutableList<Comment>, private val listener: CommentView) : BaseAdapter<Comment, CommentItemHolder>(items) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentItemHolder {
        return CommentItemHolder.create(parent.context, listener)
    }

    override fun onBindViewHolder(holder: CommentItemHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val item = getItem(position)
        holder.bind(item)
    }
}
