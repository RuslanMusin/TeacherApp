package com.summer.itis.curatorapp.model.theme

import com.summer.itis.curatorapp.model.skill.Skill
import com.summer.itis.curatorapp.model.skill.Subject
import com.summer.itis.summerproject.model.common.Identified

abstract class AbsTheme: Identified {

    override lateinit var id: String
    lateinit var title: String
    lateinit var description: String
    lateinit var subject: Subject

    var skills: MutableList<Skill> = ArrayList()
}