package com.summer.itis.curatorapp.ui.student.search.edit_choose_skill

import android.view.View
import com.summer.itis.curatorapp.model.skill.Skill
import com.summer.itis.curatorapp.ui.base.base_first.BaseRecyclerView
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragView

interface EditChooseView: BaseFragView, BaseRecyclerView<Skill>, View.OnClickListener {

    fun remove(pos: Int)
}