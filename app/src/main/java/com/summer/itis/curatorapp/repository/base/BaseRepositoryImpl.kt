package com.summer.itis.curatorapp.repository.base

import android.util.Log
import com.summer.itis.curatorapp.api.services.BaseEntityService
import com.summer.itis.curatorapp.model.user.Student
import com.summer.itis.curatorapp.utils.Const
import com.summer.itis.curatorapp.utils.Const.TAG_LOG
import com.summer.itis.curatorapp.utils.RxUtils
import com.summer.itis.summerproject.model.common.Identified
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result

abstract class BaseRepositoryImpl<ApiService: BaseEntityService<Entity>,Entity: Identified>(): BaseRepository<Entity> {

    abstract val apiService: ApiService

    override fun findById(id: String): Single<Result<Entity>> {
        return apiService.findById(id).compose(RxUtils.asyncSingle())
    }

    override fun findAll(): Single<Result<List<Student>>> {
        return apiService.findAll().compose(RxUtils.asyncSingle())
    }

    override fun createEntity(entity: Entity): Single<Result<Boolean>> {
        return apiService.createEntity(entity).compose(RxUtils.asyncSingle())
    }

    override fun updateEntity(entity: Entity): Single<Result<Boolean>> {
        return apiService.updateEntity(entity).compose(RxUtils.asyncSingle())
    }

    override fun deleteEntity(entity: Entity): Single<Result<Boolean>> {
        return apiService.deleteEntity(entity).compose(RxUtils.asyncSingle())
    }
}