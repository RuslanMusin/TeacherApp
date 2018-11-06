package com.summer.itis.curatorapp.model.comment

import com.summer.itis.summerproject.model.common.Identified
import java.util.*

class Comment: Identified {

    override lateinit var id: String
    lateinit var text: String
    lateinit var authorId: String
    var createdDate: Long = 0

    @Transient
    lateinit var authorName: String

    @Transient
    lateinit var authorPhotoUrl: String

    constructor() {}

    constructor(text: String) {
        this.text = text
        this.createdDate = Calendar.getInstance().timeInMillis
    }
}
