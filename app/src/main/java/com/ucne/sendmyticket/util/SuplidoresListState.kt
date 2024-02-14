package com.ucne.sendmyticket.util

import com.ucne.sendmyticket.data.remote.dto.SuplidoresDto

data class SuplidoresListState(
    val isLoading: Boolean = false,
    val suplidores: List<SuplidoresDto> = emptyList(),
    val error: String = ""
)
