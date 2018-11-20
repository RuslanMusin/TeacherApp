package com.summer.itis.curatorapp.ui.work.one_work_list

import com.summer.itis.curatorapp.model.skill.Skill
import com.summer.itis.curatorapp.model.work.Work
import com.summer.itis.curatorapp.repository.base.BaseRepository
import com.summer.itis.curatorapp.ui.base.base_first.BaseRecyclerView
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragView

interface OneWorkListView: BaseFragView, BaseRecyclerView<Work> {
}