package com.summer.itis.curatorapp.repository.curator

import com.summer.itis.curatorapp.api.services.BaseEntityService
import com.summer.itis.curatorapp.api.services.CuratorService
import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.repository.base.BaseRepositoryImpl

class CuratorRepositoryImpl(override val apiService: CuratorService) :
        CuratorRepository, BaseRepositoryImpl<CuratorService,Curator>() {

    override lateinit var currentCurator: Curator

}