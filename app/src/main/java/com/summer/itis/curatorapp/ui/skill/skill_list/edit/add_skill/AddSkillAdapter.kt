package com.summer.itis.curatorapp.ui.skill.skill_list.edit.add_skill

import android.view.ViewGroup
import com.summer.itis.curatorapp.model.skill.Skill
import com.summer.itis.curatorapp.ui.base.base_first.BaseAdapter
import com.summer.itis.curatorapp.ui.skill.skill_list.edit.EditSkillHolder
import com.summer.itis.curatorapp.ui.skill.skill_list.edit.EditSkillsView

class AddSkillAdapter(items: MutableList<Skill>) : BaseAdapter<Skill, AddSkillHolder>(items) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddSkillHolder {
        return AddSkillHolder.create(parent)
    }

    override fun onBindViewHolder(holder: AddSkillHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val item = getItem(position)
        holder.bind(item)
    }
}