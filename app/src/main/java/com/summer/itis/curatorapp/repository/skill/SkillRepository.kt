package com.summer.itis.curatorapp.repository.skill

import com.summer.itis.curatorapp.model.skill.Skill
import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.repository.base.BaseRepository
import io.reactivex.Single

interface SkillRepository: BaseRepository<Skill> {

    fun findMySkills(id: String): Single<List<Skill>>

}