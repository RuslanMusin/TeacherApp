package com.summer.itis.curatorapp.ui.comment

import com.arellomobile.mvp.MvpView
import com.summer.itis.curatorapp.model.comment.Comment
import com.summer.itis.curatorapp.model.user.Person
import com.summer.itis.curatorapp.ui.base.base_first.BaseAdapter
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragView
import io.reactivex.disposables.Disposable

interface CommentView: BaseFragView, BaseAdapter.OnItemClickListener<Comment>{

    fun showComments(comments: List<Comment>)

    fun showLoading(disposable: Disposable)

    fun hideLoading()

    fun onReplyClick(position: Int)

    fun onAuthorClick(userId: String)

    fun setComments(comments: List<Comment>)

    fun addComment(comment: Comment)

    fun sendComment(entityId: String)

    fun clearAfterSendComment()

    fun goToCommentAuthor(user: Person)

    fun setCommentListeners()

    fun initCommentRecycler()
}