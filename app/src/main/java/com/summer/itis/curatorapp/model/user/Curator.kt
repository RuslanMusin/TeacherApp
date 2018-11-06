package com.summer.itis.curatorapp.model.user

import com.summer.itis.curatorapp.model.skill.Skill
import com.summer.itis.curatorapp.utils.Const.OFFLINE_STATUS
import com.summer.itis.summerproject.model.common.Identified

class Curator: Person {

    constructor() {}

    constructor(email: String, username: String) {
        this.email = email
        this.name = username
    }
}