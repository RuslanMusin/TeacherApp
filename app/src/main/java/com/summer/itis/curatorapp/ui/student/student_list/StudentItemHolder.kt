package com.summer.itis.curatorapp.ui.student.student_list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.user.Student
import com.summer.itis.curatorapp.model.work.Work
import com.summer.itis.curatorapp.ui.work.one_work_list.WorkItemHolder
import com.summer.itis.curatorapp.utils.Const.SPACE
import kotlinx.android.synthetic.main.item_member.view.*

class StudentItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: Student) {
        itemView.tv_name.text = "${item.lastname + SPACE + item.name + SPACE + item.patronymic}"

        itemView.tv_group.text = "${item.groupNumber + SPACE + "," + SPACE + item.year + SPACE + itemView.context.getString(R.string.course)}"
    }


    companion object {

        fun create(parent: ViewGroup): StudentItemHolder {
            val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_member, parent, false);
            val holder = StudentItemHolder(view)
            return holder
        }
    }
}