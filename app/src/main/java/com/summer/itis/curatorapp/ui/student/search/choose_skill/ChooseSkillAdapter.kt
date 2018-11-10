package com.summer.itis.curatorapp.ui.student.search.choose_skill

import android.view.ViewGroup
import com.summer.itis.curatorapp.model.skill.Skill
import com.summer.itis.curatorapp.ui.base.base_first.BaseAdapter
import com.summer.itis.curatorapp.ui.skill.skill_list.edit.add_skill.AddSkillHolder

class ChooseSkillAdapter(items: MutableList<Skill>) : BaseAdapter<Skill, ChooseSkillHolder>(items) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChooseSkillHolder {
        return ChooseSkillHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ChooseSkillHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val item = getItem(position)
        holder.bind(item)
    }
}