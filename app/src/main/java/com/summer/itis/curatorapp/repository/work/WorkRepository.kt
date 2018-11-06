package com.summer.itis.curatorapp.repository.work

import com.summer.itis.curatorapp.model.skill.Skill
import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.model.work.Work
import com.summer.itis.curatorapp.repository.base.BaseRepository
import io.reactivex.Single

interface WorkRepository: BaseRepository<Work> {

    fun findMyWorks(id: String, dateFinish: Long?, skill: String?): Single<List<Work>>


}