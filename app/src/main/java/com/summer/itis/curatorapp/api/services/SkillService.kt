package com.summer.itis.curatorapp.api.services

import com.summer.itis.curatorapp.model.skill.Skill
import com.summer.itis.curatorapp.model.user.Curator
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface SkillService: BaseEntityService<Skill> {

    @GET("curators/{id}/subjects")
    fun findMySkills(@Path(value = "id", encoded = true) id: String): Single<List<Skill>>

}