package com.mobileprogramming.weather_sdk.di

import com.mobileprogramming.weather_sdk.AuthApi
import com.mobileprogramming.weather_sdk.data.RemoteDataSource
import com.mobileprogramming.weather_sdk.repository.BaseRepository

class AppModule {

 companion object {
     fun provideAuthApi(remoteDataSource: RemoteDataSource) : AuthApi {
         return remoteDataSource.buildApi(AuthApi::class.java)
     }

     fun provideBaseRepository(repository: BaseRepository) : BaseRepository {
         return repository
     }
 }

}