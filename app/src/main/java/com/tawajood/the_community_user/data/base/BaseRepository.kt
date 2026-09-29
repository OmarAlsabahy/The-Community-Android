package com.tawajood.the_community_user.data.base

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tawajood.the_community_user.domain.base.NetworkError
import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.utils.logE
import kotlinx.serialization.SerializationException
import retrofit2.Response
import java.io.IOException

abstract class BaseRepository {

    suspend fun <T> wrapApi(
        hasData: Boolean = true,
        apiCall: suspend () -> Response<BaseResponse<T>>
    ): RequestState<T> {
        return try {
            val response = apiCall()

            if (response.isSuccessful) {
                val body = response.body()

                if (body == null) {
                    logError("Response body is null")
                    return RequestState.Error(
                        message = "Response body is null",
                        networkError = NetworkError.RESPONSE_NULL
                    )
                }

                if (body.result == false) {
                    logError("API logical error: ${body.message} - errNum: ${body.errNum}")
                    return RequestState.Error(
                        message = body.message.orEmpty(),
                        networkError = NetworkError.API_ERROR
                    )
                }

                if (hasData) {
                    if (body.data != null) {
                        RequestState.Success(body.data)
                    } else if (body.result == true){
                        RequestState.Success(body.data as T)
                    }
                    else {
                        logError("Response body.data is null while hasData = true")
                        RequestState.Error(
                            message = body.message?.ifBlank { "Unknown error" } ?: "Unknown error",
                            networkError = NetworkError.RESPONSE_NULL
                        )
                    }
                } else {
                    @Suppress("UNCHECKED_CAST")
                    RequestState.Success(Unit as T)
                }

            } else {
                handleErrorResponse(response)
            }

        } catch (e: IOException) {
            logError("IOException: $e")
            RequestState.Error(
                message = e.message ?: "Network error",
                networkError = NetworkError.NO_INTERNET,
                exception = e
            )
        } catch (e: SerializationException) {
            logError("SerializationException: $e")
            RequestState.Error(
                message = "Serialization error",
                networkError = NetworkError.SERIALIZATION,
                exception = e
            )
        } catch (e: Exception) {
            logError("Unexpected Exception: $e")
            RequestState.Error(
                message = "Unknown error",
                networkError = NetworkError.UNKNOWN,
                exception = e
            )
        }
    }

    private fun <T> handleErrorResponse(response: Response<*>): RequestState<T> {
        val errorMessage = getErrorMessage(response.errorBody()?.string().orEmpty())
        val networkError = when (response.code()) {
            401 -> NetworkError.UNAUTHORIZED
            409 -> NetworkError.CONFLICT
            408 -> NetworkError.REQUEST_TIMEOUT
            429 -> NetworkError.TOO_MANY_REQUESTS
            413 -> NetworkError.PAYLOAD_TOO_LARGE
            500 -> NetworkError.SERVER_ERROR
            else -> NetworkError.UNKNOWN
        }
        logError("errorMessage: $errorMessage - networkError: $networkError")
        return RequestState.Error(
            message = errorMessage,
            exception = null,
            networkError = networkError
        )
    }

    private fun getErrorMessage(errorBody: String): String {
        return try {
            val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            val type = Types.newParameterizedType(BaseResponse::class.java, Any::class.java)
            val adapter = moshi.adapter<BaseResponse<Any>>(type)
            val apiError = adapter.fromJson(errorBody)
            apiError?.message ?: "An error occurred"
        } catch (e: Exception) {
            "An error occurred - $e"
        }
    }

    private fun logError(message: String) {
        message.logE()
    }
}
