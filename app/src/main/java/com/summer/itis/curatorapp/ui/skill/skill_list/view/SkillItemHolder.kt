package com.summer.itis.curatorapp.ui.skill.skill_list.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.skill.Skill
import kotlinx.android.synthetic.main.activity_login.view.*
import kotlinx.android.synthetic.main.item_skill.view.*


class SkillItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: Skill) {
        itemView.tv_skill.text = item.name
        itemView.tv_level.text = item.level
    }


    companion object {

        fun create(parent: ViewGroup): SkillItemHolder {
            val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_skill, parent, false);
            val holder = SkillItemHolder(view)
            return holder
        }
    }
}
