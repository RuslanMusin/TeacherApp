package com.summer.itis.curatorapp.ui.student.student_item

import com.summer.itis.curatorapp.model.user.Student
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragView

interface StudentView: BaseFragView {

    fun setUserData(student: Student)

}