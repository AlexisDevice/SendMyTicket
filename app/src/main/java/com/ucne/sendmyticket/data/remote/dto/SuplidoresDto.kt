package com.ucne.sendmyticket.data.remote.dto

import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SuplidoresDto(
    @PrimaryKey
    @Json(name = "idSuplidor")
    val idSuplidor: Int?,
    @Json(name = "nombres")
    val nombres: String?,
    @Json(name = "direccion")
    val direccion: String?,
    @Json(name = "telefono")
    val telefono: String?,
    @Json(name = "celular")
    val celular: String?,
    @Json(name = "fax")
    val fax: String?,
    @Json(name = "rnc")
    val rnc: String?,
    @Json(name = "tipoNcf")
    val tipoNcf: Int?,
    @Json(name = "condicion")
    val condicion: Int?,
    @Json(name = "email")
    val email: String?
)
