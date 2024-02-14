package com.ucne.sendmyticket.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.ucne.sendmyticket.data.remote.dto.SuplidoresApi
import com.ucne.sendmyticket.data.repository.SuplidoresRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideGastoApi(moshi: Moshi): SuplidoresApi {
        return Retrofit.Builder()
            .baseUrl("https://sag-api-dev.azurewebsites.net/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(SuplidoresApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSuplidoresRepository(suplidoresApi: SuplidoresApi): SuplidoresRepository {
        return SuplidoresRepository(suplidoresApi)
    }


}