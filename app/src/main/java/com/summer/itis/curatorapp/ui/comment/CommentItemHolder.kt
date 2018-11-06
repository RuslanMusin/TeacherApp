package com.summer.itis.curatorapp.ui.comment

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View

import com.bumptech.glide.Glide
import com.ms.square.android.expandabletextview.ExpandableTextView
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.comment.Comment
import com.summer.itis.curatorapp.utils.Const.STUB_PATH
import com.summer.itis.curatorapp.utils.FormatterUtil
import kotlinx.android.synthetic.main.item_comment.view.*


class CommentItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var commentClickListener: CommentView? = null

    fun bind(comment: Comment) {
        val authorId = comment.authorId

        itemView.tv_name.text = comment.authorName
        itemView.expand_text_view.text = comment.text

        val date = FormatterUtil.getRelativeTimeSpanString(itemView.context, comment.createdDate)
        itemView.tv_date.setText(date)

        itemView.iv_reply.setOnClickListener { commentClickListener?.onReplyClick(adapterPosition) }

        itemView.iv_cover.setOnClickListener { authorId?.let { it1 -> commentClickListener!!.onAuthorClick(it1) } }

        fillComment(comment)

    }

    private fun fillComment(comment: Comment) {

        if(!comment.authorPhotoUrl.equals(STUB_PATH)) {
//            val imageReference = comment.authorPhotoUrl?.let { FirebaseStorage.getInstance().reference.child(it) }

            Glide.with(itemView.getContext())
                    .load(R.drawable.teacher)
                    .into(itemView.iv_cover)
        } else {
            Glide.with(itemView.getContext())
                    .load(R.drawable.student)
                    .into(itemView.iv_cover)
        }
    }

    private fun cutLongDescription(description: String): String {
        return if (description.length < MAX_LENGTH) {
            description
        } else {
            description.substring(0, MAX_LENGTH - MORE_TEXT.length) + MORE_TEXT
        }
    }

    private fun fillComment(userName: String, comment: String, commentTextView: ExpandableTextView) {
        val contentString: Spannable = SpannableStringBuilder("$userName   $comment")
        contentString.setSpan(ForegroundColorSpan(ContextCompat.getColor(commentTextView.getContext(), R.color.highlight_text)),
                0, userName.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        commentTextView.text = contentString
    }

    companion object {

        private val MAX_LENGTH = 80
        private val MORE_TEXT = "..."

        fun create(context: Context, commentClickListener: CommentView): CommentItemHolder {
            val view = View.inflate(context, R.layout.item_comment, null)
            val holder = CommentItemHolder(view)
            holder.commentClickListener = commentClickListener
            return holder
        }
    }
}
