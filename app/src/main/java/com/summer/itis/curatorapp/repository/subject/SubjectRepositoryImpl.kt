package com.summer.itis.curatorapp.repository.subject

import com.summer.itis.curatorapp.api.services.SkillService
import com.summer.itis.curatorapp.model.skill.Skill
import com.summer.itis.curatorapp.repository.base.BaseRepositoryImpl
import io.reactivex.Single

class SubjectRepositoryImpl(override val apiService: SkillService) :
        SubjectRepository, BaseRepositoryImpl<SkillService, Skill>() {
    override fun findMySkills(id: String): Single<List<Skill>> {
        return apiService.findMySkills(id)
    }

}