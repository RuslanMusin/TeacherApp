package com.summer.itis.curatorapp.api.services

import com.summer.itis.curatorapp.model.skill.Skill
import com.summer.itis.curatorapp.model.user.Student
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.GET
import retrofit2.http.Path

interface StudentService: BaseEntityService<Student> {

    @GET("subjects")
    override fun findAll(): Single<Result<List<Student>>>

}