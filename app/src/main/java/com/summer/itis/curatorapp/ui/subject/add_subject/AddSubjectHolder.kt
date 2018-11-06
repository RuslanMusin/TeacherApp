package com.summer.itis.curatorapp.ui.subject.add_subject

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.skill.Subject
import com.summer.itis.curatorapp.model.user.Student
import com.summer.itis.curatorapp.ui.student.student_list.StudentItemHolder
import kotlinx.android.synthetic.main.item_text.view.*

class AddSubjectHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: Subject) {
        itemView.tv_name.text = "${item.name}"
    }


    companion object {

        fun create(parent: ViewGroup): AddSubjectHolder {
            val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_text, parent, false);
            val holder = AddSubjectHolder(view)
            return holder
        }
    }
}