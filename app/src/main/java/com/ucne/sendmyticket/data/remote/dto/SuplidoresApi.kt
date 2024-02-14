package com.ucne.sendmyticket.data.remote.dto

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SuplidoresApi {
    @GET("/api/SuplidoresGastos")
    suspend fun getSuplidores(): List<SuplidoresDto>

    @GET("/api/SuplidoresGastos/{id}")
    suspend fun getSuplidoresId(@Path("id") id: Int): SuplidoresDto

    @POST("/api/SuplidoresGastos")
    suspend fun postSuplidores(@Body suplidores: SuplidoresDto): Response<SuplidoresDto>
}