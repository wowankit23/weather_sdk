package com.mobileprogramming.weather_sdk.repository

import com.example.weather.data.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRepository {

    suspend fun <T> safeApiCall(
        apicall : suspend () -> T
    ) : Resource<T> {

        return withContext(Dispatchers.IO)
        {
            try {

                Resource.Success(apicall.invoke())

            }catch (throwable : Throwable)
            {
                when (throwable)
                {
                    is HttpException -> {
                        Resource.Failure(false, throwable.code(), throwable.response()?.errorBody())
                    }
                    else ->
                    {
                        Resource.Failure(true, null, null)

                    }
                }
            }

        }

    }


}