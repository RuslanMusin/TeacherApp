package com.summer.itis.curatorapp.repository.subject

import com.summer.itis.curatorapp.model.skill.Skill
import com.summer.itis.curatorapp.repository.base.BaseRepository
import io.reactivex.Single

interface SubjectRepository: BaseRepository<Skill> {

    fun findMySkills(id: String): Single<List<Skill>>

}