package com.summer.itis.curatorapp.api.services

import com.summer.itis.curatorapp.model.skill.Skill
import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.model.work.Work
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WorkService: BaseEntityService<Work> {

    @GET("curators/{id}/works?date_finish=value&skill=value")
    fun findMyWorks(@Path(value = "id", encoded = true) id: String,
                    @Query(value = "date_finish") dateFinish: Long?,
                    @Query(value = "skill") skill: String?): Single<List<Work>>

}