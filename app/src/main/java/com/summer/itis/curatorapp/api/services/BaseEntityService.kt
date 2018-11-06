package com.summer.itis.curatorapp.api.services

import com.summer.itis.curatorapp.model.user.Student
import com.summer.itis.summerproject.model.common.Identified
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result

interface BaseEntityService<Entity: Identified> {

    //CRUD
    fun findById(id: String): Single<Result<Entity>>

    fun createEntity(entity: Entity): Single<Result<Boolean>>

    fun updateEntity(entity: Entity): Single<Result<Boolean>>

    fun deleteEntity(entity: Entity): Single<Result<Boolean>>

    fun findAll(): Single<Result<List<Student>>>
}