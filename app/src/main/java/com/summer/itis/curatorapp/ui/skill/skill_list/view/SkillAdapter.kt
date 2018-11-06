package com.summer.itis.curatorapp.ui.skill.skill_list.view

import android.view.ViewGroup
import com.summer.itis.curatorapp.model.skill.Skill
import com.summer.itis.curatorapp.ui.base.base_first.BaseAdapter


class SkillAdapter(items: MutableList<Skill>) : BaseAdapter<Skill, SkillItemHolder>(items) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillItemHolder {
        return SkillItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: SkillItemHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val item = getItem(position)
        holder.bind(item)
    }
}