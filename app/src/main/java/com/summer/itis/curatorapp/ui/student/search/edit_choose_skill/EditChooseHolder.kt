package com.summer.itis.curatorapp.ui.student.search.edit_choose_skill

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.skill.Skill
import com.summer.itis.curatorapp.ui.skill.skill_list.edit.EditSkillHolder
import com.summer.itis.curatorapp.ui.skill.skill_list.edit.EditSkillsView
import com.summer.itis.curatorapp.utils.AppHelper
import kotlinx.android.synthetic.main.item_edit_skill.view.*

class EditChooseHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var listener: EditChooseView

    fun bind(item: Skill) {
        itemView.tv_skill.text = item.name
        itemView.iv_delete.setOnClickListener{ listener.remove(adapterPosition) }
    }


    companion object {

        fun create(parent: ViewGroup, listener: EditChooseView): EditChooseHolder {
            val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_remove, parent, false);
            val holder = EditChooseHolder(view)
            holder.listener = listener
            return holder
        }
    }
}