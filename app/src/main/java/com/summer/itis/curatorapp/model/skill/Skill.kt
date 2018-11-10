package com.summer.itis.curatorapp.model.skill

import com.summer.itis.summerproject.model.common.Identified

class Skill: Identified {

    override lateinit var id: String

    lateinit var name: String

    lateinit var level: String

    override fun equals(other: Any?): Boolean {
        if(other != null) {
            if (other.javaClass.canonicalName.equals(this.javaClass.canonicalName)) {
                val skill = other as Skill
                if(skill.name.equals(this.name) && skill.level.equals(this.level)) {
                    return true
                }
                return false
            }
            return false
        }
        return false
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + level.hashCode()
        return result
    }

}