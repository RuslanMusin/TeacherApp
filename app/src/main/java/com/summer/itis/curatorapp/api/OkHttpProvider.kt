package com.summer.itis.curatorapp.api
import okhttp3.OkHttpClient

object OkHttpProvider {

    @Volatile
    private var sClient: OkHttpClient? = null

    fun provideClient(): OkHttpClient? {
        var client = sClient
        if (client == null) {
            synchronized(ApiFactory::class.java) {
                client = sClient
                if (client == null) {
                    sClient = buildMessageClient()
                    client = sClient
                }
            }
        }
        return client
    }

    fun recreate() {
        sClient = null
        sClient = buildMessageClient()
    }

    private fun buildClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    private fun buildMessageClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(ApiKeyInterceptor.create())
                .addInterceptor(ErrorHandlingInterceptor.create())
                .build()
    }
}
