package com.summer.itis.curatorapp.ui.skill.skill_list.edit

import android.view.View
import com.summer.itis.curatorapp.model.skill.Skill
import com.summer.itis.curatorapp.ui.base.base_first.BaseRecyclerView
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragView

interface EditSkillsView: BaseFragView, BaseRecyclerView<Skill>, View.OnClickListener {

    fun remove(pos: Int)

    fun chooseLevel(pos: Int, level: String)
}