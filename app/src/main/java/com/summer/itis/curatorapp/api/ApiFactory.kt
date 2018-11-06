package com.summer.itis.curatorapp.api

import android.util.Log
import com.summer.itis.curatorapp.BuildConfig
import com.summer.itis.curatorapp.api.OkHttpProvider
import com.summer.itis.curatorapp.api.services.*
import com.summer.itis.curatorapp.model.common.APIError
import com.summer.itis.curatorapp.model.work.Work
import com.summer.itis.curatorapp.utils.Const.TAG_LOG
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory






class ApiFactory {

    companion object {

        private fun buildRetrofit(): Retrofit {
            return Retrofit.Builder()
                    .baseUrl(BuildConfig.API_ENDPOINT)
                    .client(OkHttpProvider.provideClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
        }

        val curatorService: CuratorService by lazy {
            buildRetrofit().create(CuratorService::class.java)
        }

        val commonService: CommonService by lazy {
            buildRetrofit().create(CommonService::class.java)
        }

        val skillService: SkillService by lazy {
            buildRetrofit().create(SkillService::class.java)
        }

        val workService: WorkService by lazy {
            buildRetrofit().create(WorkService::class.java)
        }

        val studentService: StudentService by lazy {
            buildRetrofit().create(StudentService::class.java)
        }

        val errorConverter: Converter<ResponseBody, APIError> by lazy {
            val converter: Converter<ResponseBody, APIError> = ApiFactory.buildRetrofit()
                    .responseBodyConverter(APIError::class.java, arrayOfNulls<Annotation>(0))

            converter
        }

    }

}
