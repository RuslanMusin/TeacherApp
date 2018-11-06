package com.summer.itis.curatorapp.model.skill

import com.summer.itis.summerproject.model.common.Identified

class Skill: Identified {

    override lateinit var id: String

    lateinit var name: String

    lateinit var level: String

}