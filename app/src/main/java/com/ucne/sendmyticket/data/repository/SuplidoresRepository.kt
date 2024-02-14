package com.ucne.sendmyticket.data.repository

import com.ucne.sendmyticket.data.remote.dto.SuplidoresApi
import com.ucne.sendmyticket.data.remote.dto.SuplidoresDto
import com.ucne.sendmyticket.util.Resource
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import retrofit2.HttpException

class SuplidoresRepository @Inject constructor(
    private val api: SuplidoresApi
) {
    fun getSuplidores(): Flow<Resource<List<SuplidoresDto>>> = flow {
        try {
            emit(Resource.Loading())

            val gastos = api.getSuplidores()

            emit(Resource.Success(gastos))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message ?: "Error HTTP GENERAL"))
        } catch (e: IOException) {
            emit(Resource.Error(e.message ?: "verificar tu conexion a internet"))
        }
    }

    fun getSuplidoresId(id: Int): Flow<Resource<SuplidoresDto>> = flow {
        try {
            emit(Resource.Loading())
            val gastos = api.getSuplidoresId(id)

            emit(Resource.Success(gastos))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message ?: "Error HTTP GENERAL"))
        } catch (e: IOException) {
            emit(Resource.Error(e.message ?: "verificar tu conexion a internet"))
        }
    }

    suspend fun postSuplidores(suplidoresDto: SuplidoresDto) {
        api.postSuplidores(suplidoresDto)
    }


}