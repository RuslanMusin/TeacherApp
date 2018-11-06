package com.summer.itis.curatorapp.ui.subject.add_subject

import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.model.skill.Subject
import com.summer.itis.curatorapp.repository.RepositoryProvider.Companion.studentRepository
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer

@InjectViewState
class AddSubjectPresenter(): BaseFragPresenter<AddSubjectView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun loadSubjects() : List<Subject>{
        val subjects: MutableList<Subject> = ArrayList()


        for(i in 1..10) {
            val subject = Subject()
            subject.id = "$i"
            if(i / 2 == 0) {
                subject.name = "Android $i"
            } else {
                subject.name = "Java EE $i"
            }
            subjects.add(subject)
        }
        return subjects
        /*val disposable = studentRepository
                .findAll()
                .doOnSubscribe(Consumer<Disposable> { viewState.showLoading(it) })
                .doAfterTerminate(Action { viewState.hideLoading() })
                .subscribe ({ viewState.changeDataSet(it) }, { viewState.handleError(it) })
        compositeDisposable.add(disposable)*/
    }

}