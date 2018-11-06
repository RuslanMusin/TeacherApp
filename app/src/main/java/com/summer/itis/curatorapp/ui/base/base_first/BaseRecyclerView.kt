package com.summer.itis.curatorapp.ui.base.base_first

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import io.reactivex.disposables.Disposable

interface BaseRecyclerView<Entity> : BaseAdapter.OnItemClickListener<Entity> {

    fun handleError(throwable: Throwable)

    fun setNotLoading()

    fun showLoading(disposable: Disposable)

    fun hideLoading()

    fun loadNextElements(i: Int)

    fun changeDataSet(tests: List<Entity>)
}
