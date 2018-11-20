package com.summer.itis.curatorapp.ui.skill.skill_list.edit

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.skill.Skill
import com.summer.itis.curatorapp.utils.AppHelper
import kotlinx.android.synthetic.main.item_edit_skill.view.*
import kotlinx.android.synthetic.main.layout_item_tv_with_clear.view.*


class EditSkillHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var listener: EditSkillsView

    fun bind(item: Skill) {
        itemView.tv_added_skill_name.text = item.name
        itemView.tv_added_skill_level.text = itemView.context.getString(R.string.skill_level, item.level)
        itemView.iv_remove_skill.setOnClickListener { listener.remove(adapterPosition) }
        /*itemView.tv_skill.text = item.name
        itemView.spinner.selectedIndex = AppHelper.getLevelInt(item.level, itemView.context)
        itemView.iv_delete.setOnClickListener{ listener.remove(adapterPosition) }
        itemView.context.let {
            itemView.spinner.setItems(it.getText(R.string.low_level), it.getText(R.string.medium_level), it.getText(R.string.high_level))
        }
        itemView.spinner.setOnItemSelectedListener { view, position, id, spItem ->
//            Snackbar.make(view, "Clicked $item", Snackbar.LENGTH_LONG).show()
            listener.chooseLevel(adapterPosition, spItem as String)
        }*/
    }


    companion object {

        fun create(parent: ViewGroup, listener: EditSkillsView): EditSkillHolder {
            val view =  LayoutInflater.from(parent.context).inflate(R.layout.layout_item_tv_with_clear, parent, false);
            val holder = EditSkillHolder(view)
            holder.listener = listener
            return holder
        }
    }
}