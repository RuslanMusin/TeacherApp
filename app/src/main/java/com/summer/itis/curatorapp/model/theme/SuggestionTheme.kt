package com.summer.itis.curatorapp.model.theme

import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.model.user.Student
import com.summer.itis.curatorapp.utils.Const.STUDENT_TYPE
import com.summer.itis.curatorapp.utils.Const.WAITING_CURATOR
import com.summer.itis.summerproject.model.common.Identified
import java.util.*

class SuggestionTheme: Identified {

    lateinit override var id: String

    lateinit var themeId: String
    lateinit var curatorId: String
    lateinit var studentId: String
    lateinit var themeProgressId: String
    var status: String = WAITING_CURATOR
    lateinit var dateCreation: Date
    var type: String = STUDENT_TYPE

    var curator: Curator? = null
    var student: Student? = null
    var themeProgress: ThemeProgress? = null
    var theme: Theme? = null
}