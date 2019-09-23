package com.elmenus.elmenustask.base.data.remote.interceptor


import com.elmenus.elmenustask.base.data.exception.APIException
import com.elmenus.elmenustask.base.data.remote.RemoteConstants
import com.elmenus.elmenustask.base.resources.repository.ResourcesRepository
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException
import java.net.SocketTimeoutException
import java.nio.charset.Charset


class ErrorMappingInterceptor(

    private val resourcesRepository: ResourcesRepository,
    private val gsonPareser: Gson
) : Interceptor {

    private val successStatus = 1
    private val keyJson = "json"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response: Response


        try {
            response = chain.proceed(request)
        } catch (e: IOException) {
            if (e is SocketTimeoutException) {
                throw APIException(
                    RemoteConstants.TIME_OUT_STATUS_CODE,
                    resourcesRepository.getSocketTimeoutExceptionMessage()
                )
            } else {
                throw APIException(
                    RemoteConstants.LOST_CONNECTION_STATUS_CODE,
                    resourcesRepository.getNetworkExceptionMessage()
                )
            }
        }

        val body = response.body()!!
        // Only intercept JSON type responses and ignore the rest.
        if (isJsonTypeResponse(body)) {
            val source = body.source()
            source.request(java.lang.Long.MAX_VALUE) // Buffer the entire body.
            val buffer = source.buffer()
            val charset = body.contentType()!!.charset(Charset.forName("UTF-8"))!!
            // Clone the existing buffer is they can only read once so we still want to pass the original one to the chain.
            val responseBody = buffer.clone().readString(charset)
            if (response.isSuccessful.not()) {
                if (response.code() == 500) {
                    throw APIException(
                        RemoteConstants.SERVER_ERROR,
                        resourcesRepository.getSocketTimeoutExceptionMessage()
                    )
                } else {
                    val apiException = gsonPareser.fromJson(responseBody, APIException::class.java)
                    apiException.code = response.code()
                    throw apiException
                }
            }
        } else {
            throw APIException(
                RemoteConstants.SERVER_ERROR,
                resourcesRepository.getGenericUnknownMessage()
            )
        }

        return response
    }

    private fun isJsonTypeResponse(body: ResponseBody?): Boolean {
        return body?.contentType() != null && body.contentType()!!.subtype().toLowerCase() == keyJson
    }


}