package com.summer.itis.curatorapp.model.api_result

class LoginResult {

    lateinit var status: String
    lateinit var accessToken: String
    lateinit var userId: String
    var isSuccessful: Boolean = true //по идее false at start
}