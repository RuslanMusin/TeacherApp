package com.summer.itis.curatorapp.utils

import android.content.Context
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.R.string.skills
import com.summer.itis.curatorapp.model.skill.Skill

class SkillViewHelper {

    companion object {


        fun getListString(list: List<String>): String {
            var listString: String = ""
            for ((i, item) in list.withIndex()) {
                listString += item
                if (i != list.lastIndex) {
                    listString += " , "
                }

            }
            listString.removeSuffix(",")
            return listString
        }

        fun getSkillsText(skills: List<Skill>, context: Context): String {
            if(skills.size != 0) {
                val listSkills: MutableList<String> = ArrayList()
                for(i in skills.indices) {
                    listSkills.add("${skills[i].name} ${context.getString(R.string.level)} ${skills[i].level}")
                }
                return getListString(listSkills)
            } else {
                return context.getString(R.string.doesnt_matter_for_all)
            }

        }
    }

}
