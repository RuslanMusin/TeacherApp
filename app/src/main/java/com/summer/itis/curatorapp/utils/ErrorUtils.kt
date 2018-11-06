package com.summer.itis.curatorapp.utils

import com.summer.itis.curatorapp.api.ApiFactory
import com.summer.itis.curatorapp.api.ApiFactory.Companion.errorConverter
import com.summer.itis.curatorapp.model.common.APIError
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.IOException
import retrofit2.Converter


object ErrorUtils {

    fun parseError(response: Response<*>): APIError {

        var error: APIError = APIError()

        try {
            response.errorBody()?.let { error = errorConverter.convert(it) }
        } catch (e: IOException) {
            return APIError()
        }

        return error
    }
}