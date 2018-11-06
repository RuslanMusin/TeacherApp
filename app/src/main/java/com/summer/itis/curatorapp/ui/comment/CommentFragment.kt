package com.summer.itis.curatorapp.ui.comment

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.arellomobile.mvp.MvpAppCompatFragment
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.R.id.*
import com.summer.itis.curatorapp.model.comment.Comment
import com.summer.itis.curatorapp.model.user.Person
import com.summer.itis.curatorapp.ui.base.base_first.activity.BaseActivity
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragment
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const.TAG_LOG
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.layout_add_comment.*
import kotlinx.android.synthetic.main.layout_recycler_list.*
import java.util.*

abstract class CommentFragment<Presenter: CommentPresenter<*>>: BaseFragment<Presenter>(), CommentView {

    protected lateinit var adapter: CommentAdapter

    private var comments: MutableList<Comment> = ArrayList()

    abstract var presenter: Presenter

    protected abstract var entityId: String
//    protected abstract var repository: CommentRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCommentRecycler()
        setCommentListeners()
//        presenter.repository = repository
    }

    override fun setCommentListeners() {
        et_comment.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                Log.d(TAG_LOG, "char length = " + charSequence.length)
                sendButton.isEnabled = charSequence.toString().trim { it <= ' ' }.length > 0
                Log.d(TAG_LOG, "enabled = " + sendButton.isEnabled)
            }

            override fun afterTextChanged(editable: Editable) {
                val charSequence = editable.toString()
                Log.d(TAG_LOG, "after char length = " + charSequence.length)
                sendButton.isEnabled = charSequence.trim { it <= ' ' }.length > 0
                Log.d(TAG_LOG, "enabled = " + sendButton.isEnabled)
            }
        })

        sendButton.setOnClickListener {
            if ((activity as BaseActivity<*>).hasInternetConnection()) {
                sendComment(entityId)
            } else {
                (activity as BaseActivity<*>).showSnackBar(R.string.internet_connection_failed)
            }
        }
    }

    override fun initCommentRecycler() {
        adapter = CommentAdapter(ArrayList(), this)
        val manager = LinearLayoutManager(this.activity)
        rv_list.setLayoutManager(manager)
        adapter.attachToRecyclerView(rv_list)
        adapter.setOnItemClickListener(this)
    }

    override fun onReplyClick(position: Int) {
        et_comment.isEnabled = true
        val comment = comments.get(position)
        val commentString = comment.authorName + ", "
        et_comment.setText(commentString)
        et_comment.isPressed = true
        et_comment.setSelection(commentString.length)
    }

    override fun onAuthorClick(userId: String) {
        presenter.openCommentAuthor(userId)
    }

    override fun goToCommentAuthor(user: Person) {
//        PersonalActivity.start(activity as Activity, user)
    }

    override fun setComments(comments: List<Comment>) {
        this.comments = comments.toMutableList()
        adapter.changeDataSet(comments)
    }

    override fun addComment(comment: Comment) {
        comments.add(comment)
        adapter.changeDataSet(comments)
    }

    override fun onItemClick(item: Comment) {
    }

    override fun showComments(comments: List<Comment>) {
        this.comments = comments.toMutableList()
        adapter.changeDataSet(comments)
    }

    override fun sendComment(entityId: String) {
        Log.d(TAG_LOG, "focus down")
        val commentText = et_comment.getText().toString()
        Log.d(TAG_LOG, "send comment = $commentText")
        if (commentText.length > 0) {
            val comment = Comment()
            val user = AppHelper.currentCurator
            user.let {
                comment.text = commentText
                comment.authorId = user.id
                comment.authorName = user.name
                comment.authorPhotoUrl = user.photoUrl
                comment.createdDate = (Date().time)
                presenter.createComment(entityId, comment)
            }
            addComment(comment)
        }
        clearAfterSendComment()
    }

    override fun showLoading(disposable: Disposable) {
        pb_list.visibility = View.VISIBLE

    }

    override fun hideLoading() {
        pb_list.visibility = View.GONE

    }

}