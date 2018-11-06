package com.summer.itis.curatorapp.ui.comment

import com.arellomobile.mvp.MvpPresenter
import com.summer.itis.curatorapp.model.comment.Comment
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const.STUB_PATH
import java.util.*

open class CommentPresenter<View: CommentView>: BaseFragPresenter<View>() {

//    internal lateinit var repository: CommentRepository

    fun loadComments(entityId: String) {
        val list: MutableList<Comment> = ArrayList()
        for(i in 1..10) {
            val comment = Comment()
            comment.authorId = "$i"
            comment.id = "$i"
            comment.authorName = AppHelper.currentCurator.name
            comment.authorPhotoUrl = STUB_PATH
            comment.createdDate = Date().time
            comment.text = "Simple comment $i"
            list.add(comment)
        }
        viewState.hideLoading()
        viewState.showComments(list)
       /* repository.getComments(entityId)
                .doOnSubscribe({ viewState.showLoading(it) })
                .doAfterTerminate({ viewState.hideLoading() })
                .subscribe{ comments ->
                    viewState?.showComments(comments)
                }*/
    }

    fun createComment(crossingId: String, comment: Comment) {
       /* repository.setKey(comment)
        repository.createComment(crossingId,comment)
                .subscribe()*/
    }

    fun openCommentAuthor(userId: String) {
       /* userRepository.findById(userId).subscribe{ user ->
            viewState.goToCommentAuthor(user)
        }*/
    }

}