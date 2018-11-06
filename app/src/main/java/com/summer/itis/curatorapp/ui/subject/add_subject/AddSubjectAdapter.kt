package com.summer.itis.curatorapp.ui.subject.add_subject

import android.view.ViewGroup
import com.summer.itis.curatorapp.model.skill.Subject
import com.summer.itis.curatorapp.model.user.Student
import com.summer.itis.curatorapp.ui.base.base_first.BaseAdapter
import com.summer.itis.curatorapp.ui.student.student_list.StudentItemHolder

class AddSubjectAdapter(items: MutableList<Subject>) : BaseAdapter<Subject, AddSubjectHolder>(items) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddSubjectHolder {
        return AddSubjectHolder.create(parent)
    }

    override fun onBindViewHolder(holder: AddSubjectHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val item = getItem(position)
        holder.bind(item)
    }
}