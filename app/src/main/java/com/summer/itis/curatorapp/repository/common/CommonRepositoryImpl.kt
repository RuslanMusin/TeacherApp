package com.summer.itis.curatorapp.repository.common

import android.content.ClipData
import android.util.Log
import com.summer.itis.curatorapp.api.ApiFactory.Companion.commonService
import com.summer.itis.curatorapp.model.api_result.LoginBody
import com.summer.itis.curatorapp.model.api_result.LoginResult
import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.repository.common.CommonRepository
import com.summer.itis.curatorapp.utils.RxUtils



import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result
import retrofit2.adapter.rxjava2.Result.response
import com.summer.itis.curatorapp.utils.ErrorUtils
import com.summer.itis.curatorapp.model.common.APIError



class CommonRepositoryImpl : CommonRepository {

    override fun login(loginBody: LoginBody): Single<Result<LoginResult>> {
        return commonService
            .login(loginBody)
            .compose(RxUtils.asyncSingle())
    }
}
