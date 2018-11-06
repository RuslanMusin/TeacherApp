package com.summer.itis.curatorapp.api.services

import com.summer.itis.curatorapp.model.api_result.LoginBody
import com.summer.itis.curatorapp.model.api_result.LoginResult
import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.repository.base.BaseRepository
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.*

interface CuratorService: BaseEntityService<Curator> {

    @GET("curators/{id}")
    override fun findById(@Path(value = "id", encoded = true) id: String): Single<Result<Curator>>

}
