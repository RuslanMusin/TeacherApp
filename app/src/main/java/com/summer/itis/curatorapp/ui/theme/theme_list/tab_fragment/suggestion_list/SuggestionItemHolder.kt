package com.summer.itis.curatorapp.ui.theme.theme_list.tab_fragment.suggestion_list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.R.id.tv_status
import com.summer.itis.curatorapp.model.theme.SuggestionTheme
import com.summer.itis.curatorapp.utils.Const.ACCEPTED_BOTH
import com.summer.itis.curatorapp.utils.Const.CHANGED_CURATOR
import com.summer.itis.curatorapp.utils.Const.CHANGED_STUDENT
import com.summer.itis.curatorapp.utils.Const.IN_PROGRESS_CURATOR
import com.summer.itis.curatorapp.utils.Const.IN_PROGRESS_STUDENT
import com.summer.itis.curatorapp.utils.Const.REJECTED_CURATOR
import com.summer.itis.curatorapp.utils.Const.REJECTED_STUDENT
import com.summer.itis.curatorapp.utils.Const.WAITING_CURATOR
import com.summer.itis.curatorapp.utils.Const.WAITING_STUDENT
import kotlinx.android.synthetic.main.item_suggestion.view.*

class SuggestionItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var listener: SuggestionListView

    fun bind(item: SuggestionTheme) {
        itemView.tv_theme.text = item.themeProgress?.title
        itemView.tv_subject.text = item.theme?.subject?.name
        itemView.tv_status.text = setStatus(item.status)
        itemView.setOnLongClickListener {
            listener.chooseUserFakeAction(adapterPosition)
            true
        }
    }


    companion object {

        fun create(parent: ViewGroup, listener: SuggestionListView): SuggestionItemHolder {
            val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_suggestion, parent, false);
            val holder = SuggestionItemHolder(view)
            holder.listener = listener
            return holder
        }
    }

    fun setStatus(status: String): String {
        var uiStatus = ""
        itemView.context.let {
            when (status) {

                WAITING_CURATOR -> uiStatus = it.getString(R.string.waiting_curator)

                WAITING_STUDENT -> uiStatus = it.getString(R.string.waiting_student)

                ACCEPTED_BOTH -> uiStatus = it.getString(R.string.accepted_both)

                REJECTED_CURATOR -> uiStatus = it.getString(R.string.rejected_curator)

                REJECTED_STUDENT -> uiStatus = it.getString(R.string.rejected_student)

                IN_PROGRESS_CURATOR -> uiStatus = it.getString(R.string.in_progress_curator)

                IN_PROGRESS_STUDENT -> uiStatus = it.getString(R.string.in_progress_student)

                CHANGED_CURATOR -> uiStatus = it.getString(R.string.changed_curator)

                CHANGED_STUDENT -> uiStatus = it.getString(R.string.changed_student)

            }

            return uiStatus
        }
    }

}
