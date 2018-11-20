package com.summer.itis.curatorapp.model.theme

import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.model.user.Student
import com.summer.itis.curatorapp.utils.Const.ALL_CHOOSED
import com.summer.itis.curatorapp.utils.Const.STUDENT_TYPE
import java.util.*

class Theme: AbsTheme() {

    lateinit var curatorId: String
    lateinit var studentId: String
    lateinit var subjectId: String

    lateinit var dateCreation: Date
    var dateAcceptance: Date? = null

    var curator: Curator? = null
    var student: Student? = null

    var targetType: String = ALL_CHOOSED
}