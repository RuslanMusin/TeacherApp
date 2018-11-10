package com.summer.itis.curatorapp.ui.student.search.choose_skill

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.skill.Skill
import com.summer.itis.curatorapp.ui.skill.skill_list.edit.add_skill.AddSkillHolder
import kotlinx.android.synthetic.main.item_skill.view.*
import kotlinx.android.synthetic.main.item_text.view.*

class ChooseSkillHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: Skill) {
        itemView.tv_name.text = item.name
    }


    companion object {

        fun create(parent: ViewGroup): ChooseSkillHolder {
            val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_text, parent, false);
            val holder = ChooseSkillHolder(view)
            return holder
        }
    }
}