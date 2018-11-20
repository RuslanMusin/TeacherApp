package com.summer.itis.curatorapp.ui.work.work_list.tab_fragment.my_work_list

import com.summer.itis.curatorapp.model.work.Work
import com.summer.itis.curatorapp.ui.base.base_custom.SearchListener
import com.summer.itis.curatorapp.ui.base.base_first.BaseRecyclerView
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragView

interface MyWorkListView: BaseFragView, BaseRecyclerView<Work>, SearchListener {
}