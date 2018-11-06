package com.summer.itis.curatorapp.ui.student.student_list

import com.summer.itis.curatorapp.model.user.Student
import com.summer.itis.curatorapp.ui.base.base_first.BaseRecyclerView
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragView

interface StudentListView: BaseFragView, BaseRecyclerView<Student> {
}