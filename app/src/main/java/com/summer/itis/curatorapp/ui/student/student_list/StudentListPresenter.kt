package com.summer.itis.curatorapp.ui.student.student_list

import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import io.reactivex.disposables.CompositeDisposable

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