package com.summer.itis.curatorapp.model.work

import com.summer.itis.curatorapp.model.theme.Theme
import com.summer.itis.summerproject.model.common.Identified
import java.util.*

class Work: Identified {

    override lateinit var id: String

    lateinit var theme: Theme

    lateinit var dateStart: Date
    var dateFinish: Date? = null



}