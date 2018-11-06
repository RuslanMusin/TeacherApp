package com.summer.itis.curatorapp.repository.curator

import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.repository.base.BaseRepository

interface CuratorRepository: BaseRepository<Curator> {

    var currentCurator: Curator

}