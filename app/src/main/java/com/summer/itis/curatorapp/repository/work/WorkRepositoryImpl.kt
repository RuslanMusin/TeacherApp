package com.summer.itis.curatorapp.repository.work

import com.summer.itis.curatorapp.api.services.CuratorService
import com.summer.itis.curatorapp.api.services.WorkService
import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.model.work.Work
import com.summer.itis.curatorapp.repository.base.BaseRepositoryImpl
import com.summer.itis.curatorapp.repository.curator.CuratorRepository
import io.reactivex.Single

class WorkRepositoryImpl(override val apiService: WorkService) :
        WorkRepository, BaseRepositoryImpl<WorkService, Work>() {

    override fun findMyWorks(id: String, dateFinish: Long?, skill: String?): Single<List<Work>> {
        return apiService.findMyWorks(id, dateFinish, skill)
    }

}