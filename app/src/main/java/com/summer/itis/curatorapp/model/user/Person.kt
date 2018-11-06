package com.summer.itis.curatorapp.model.user

import com.summer.itis.curatorapp.model.skill.Skill
import com.summer.itis.curatorapp.model.theme.SuggestionTheme
import com.summer.itis.curatorapp.model.theme.Theme
import com.summer.itis.curatorapp.utils.Const.OFFLINE_STATUS
import com.summer.itis.curatorapp.utils.Const.STUB_PATH
import com.summer.itis.summerproject.model.common.Identified

abstract class Person: Identified {

    override lateinit var id: String

    lateinit var email: String
    lateinit var name: String
    lateinit var lastname: String
    lateinit var patronymic: String
    var photoUrl: String = STUB_PATH
    var description: String = "STANDART_DESC"
    var isStandartPhoto: Boolean = true
    var status: String = OFFLINE_STATUS

    @Transient
    var skills: MutableList<Skill> = ArrayList()
    @Transient
    var suggestions: MutableList<SuggestionTheme> = ArrayList()
    @Transient
    var themes: MutableList<Theme> = ArrayList()

    fun printFullName(): String {
        return "$lastname $name $patronymic"
    }
}