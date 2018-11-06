package com.summer.itis.curatorapp.ui.student.student_item

import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.R.drawable.student
import com.summer.itis.curatorapp.model.user.Student
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.ui.curator.curator_item.view.CuratorView

@InjectViewState
class StudentPresenter(): BaseFragPresenter<StudentView>() {

    fun findStudentById(id: String) {
        val student = Student()
        student.id = id
        student.name = "Ruslan"
        student.lastname = "Musin"
        student.patronymic = "Martovich"
        student.description = "usual desc"
        student.groupNumber = "11-603"
        student.year = 3
        viewState.setUserData(student)
    }

}