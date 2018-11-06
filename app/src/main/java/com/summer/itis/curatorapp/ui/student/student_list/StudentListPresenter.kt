package com.summer.itis.curatorapp.ui.student.student_list

import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.repository.RepositoryProvider.Companion.studentRepository
import com.summer.itis.curatorapp.repository.RepositoryProvider.Companion.worksRepository
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.ui.work.one_work_list.WorkListView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer

@InjectViewState
class StudentListPresenter(): BaseFragPresenter<StudentListView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()


    fun loadStudents() {
        /*val disposable = studentRepository
                .findAll()
                .doOnSubscribe(Consumer<Disposable> { viewState.showLoading(it) })
                .doAfterTerminate(Action { viewState.hideLoading() })
                .subscribe ({ viewState.changeDataSet(it) }, { viewState.handleError(it) })
        compositeDisposable.add(disposable)*/
    }

}