package com.summer.itis.curatorapp.ui.student.search.edit_choose_skill

import android.view.ViewGroup
import com.summer.itis.curatorapp.model.skill.Skill
import com.summer.itis.curatorapp.ui.base.base_first.BaseAdapter
import com.summer.itis.curatorapp.ui.skill.skill_list.edit.EditSkillHolder
import com.summer.itis.curatorapp.ui.skill.skill_list.edit.EditSkillsView

class EditChooseAdapter(items: MutableList<Skill>, private val editListener: EditChooseView) : BaseAdapter<Skill, EditChooseHolder>(items) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditChooseHolder {
        return EditChooseHolder.create(parent, editListener)
    }

    override fun onBindViewHolder(holder: EditChooseHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val item = getItem(position)
        holder.bind(item)
    }
}