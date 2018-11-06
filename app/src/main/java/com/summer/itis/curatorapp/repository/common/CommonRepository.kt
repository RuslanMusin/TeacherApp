package com.summer.itis.curatorapp.repository.common



import com.summer.itis.curatorapp.model.api_result.LoginBody
import com.summer.itis.curatorapp.model.api_result.LoginResult
import com.summer.itis.curatorapp.model.user.Curator
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result

interface CommonRepository {

    fun login(loginBody: LoginBody): Single<Result<LoginResult>>
}
