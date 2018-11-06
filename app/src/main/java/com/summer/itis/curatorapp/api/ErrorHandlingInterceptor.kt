package com.summer.itis.curatorapp.api

import com.summer.itis.curatorapp.utils.Const.APP_JSON
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import com.summer.itis.curatorapp.ui.login.LoginActivity


class ErrorHandlingInterceptor private constructor() : Interceptor {

    companion object {

        fun create(): Interceptor {
            return ErrorHandlingInterceptor()
        }
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        // todo deal with the issues the way you need to
        when (response.code()) {

            500 -> showError()

            401 -> showLoginPage()

            200 -> return response

        }

        return response
    }

    private fun showError() {

    }

    private fun showLoginPage() {

    }
}
