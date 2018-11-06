package com.summer.itis.curatorapp.ui.skill.skill_list.view

import com.summer.itis.curatorapp.model.skill.Skill
import com.summer.itis.curatorapp.repository.base.BaseRepository
import com.summer.itis.curatorapp.ui.base.base_first.BaseRecyclerView
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragView

interface SkillListView: BaseFragView, BaseRecyclerView<Skill> {
}