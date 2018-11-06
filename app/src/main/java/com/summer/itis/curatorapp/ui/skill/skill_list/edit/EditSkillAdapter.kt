package com.summer.itis.curatorapp.ui.skill.skill_list.edit

import android.view.ViewGroup
import com.summer.itis.curatorapp.model.skill.Skill
import com.summer.itis.curatorapp.ui.base.base_first.BaseAdapter

class EditSkillAdapter(items: MutableList<Skill>, private val editListener: EditSkillsView) : BaseAdapter<Skill, EditSkillHolder>(items) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditSkillHolder {
        return EditSkillHolder.create(parent, editListener)
    }

    override fun onBindViewHolder(holder: EditSkillHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val item = getItem(position)
        holder.bind(item)
    }
}