package com.ucne.sendmyticket.ui.SuplidoresGastos

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucne.sendmyticket.data.remote.dto.SuplidoresDto
import com.ucne.sendmyticket.data.repository.SuplidoresRepository
import com.ucne.sendmyticket.util.Resource
import com.ucne.sendmyticket.util.SuplidoresListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SuplidoresViewModel @Inject constructor(
    private val suplidoresRepository: SuplidoresRepository
): ViewModel(){

    var tipoNcf by mutableStateOf(0)
    var nombres by mutableStateOf("")
    var rnc by mutableStateOf("")
    var direccion by mutableStateOf("")
    var telefono by mutableStateOf("")
    var celular by mutableStateOf("")
    var fax by mutableStateOf("")
    var idSuplidor by mutableStateOf(0)
    var condicion by mutableStateOf(0)
    var email by mutableStateOf("")

    var isValidRnc by mutableStateOf(true)
    var isValidTipoNcf by mutableStateOf(true)
    var isValidNombres by mutableStateOf(true)
    var isValidFax by mutableStateOf(true)
    var isValidDireccion by mutableStateOf(true)
    var isValidCelular by mutableStateOf(true)
    var isValidTelefono by mutableStateOf(true)
    var isValidCondicion by mutableStateOf(true)
    var isValidEmail by mutableStateOf(true)

    fun isValid(): Boolean {
        isValidNombres = nombres.isNotBlank()
        isValidRnc = rnc.isNotBlank()
        isValidDireccion = direccion.isNotBlank()
        isValidCondicion = condicion > 0
        isValidEmail = email.isNotBlank()
        isValidCelular = celular.isNotBlank()
        isValidTelefono = telefono.isNotBlank()
        isValidFax = fax.isNotBlank()
        isValidTipoNcf = tipoNcf > 0

        return isValidNombres && isValidRnc && isValidDireccion && isValidCondicion && isValidCondicion && isValidEmail
                && isValidCelular && isValidTelefono && isValidFax && isValidTipoNcf
    }

    private val _uiState = MutableStateFlow(SuplidoresListState())
    val uiState: StateFlow<SuplidoresListState> = _uiState.asStateFlow()

    fun cargar() {
        suplidoresRepository.getSuplidores().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }

                is Resource.Success -> {
                    _uiState.update { it.copy(suplidores = result.data ?: emptyList()) }
                }

                is Resource.Error -> {
                    _uiState.update { it.copy(error = result.message ?: "Error desconocido") }
                }

                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    init {
        cargar()
    }

    fun guardarSuplidor() {
        viewModelScope.launch {
            val suplidores = SuplidoresDto(
                idSuplidor = 0,
                tipoNcf=tipoNcf,
                nombres = nombres,
                direccion = direccion,
                telefono = telefono,
                rnc = rnc,
                celular = celular,
                condicion = condicion,
                email = email,
                fax = fax,
            )
            suplidoresRepository.postSuplidores(suplidores)
            limpiar()
            cargar()
        }
    }

    fun getSuplidoresById(id: Int) {
        viewModelScope.launch {
            suplidoresRepository.getSuplidoresId(id).onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        val supply = result.data
                        tipoNcf = tipoNcf
                        tipoNcf=tipoNcf
                        nombres = nombres
                        direccion = direccion
                        telefono = telefono
                        rnc = rnc
                        celular = celular
                        condicion = condicion
                        email = email
                        fax = fax
                        idSuplidor = idSuplidor
                    }

                    is Resource.Error -> {
                        _uiState.update { it.copy(error = result.message ?: "Error desconocido") }
                    }

                    else -> {}
                }
            }.launchIn(viewModelScope)
        }
    }

    fun limpiar() {
        tipoNcf = 0
        nombres = ""
        direccion = ""
        telefono = ""
        rnc = ""
        celular = ""
        condicion = 0
        email = ""
        fax = ""
        idSuplidor = 0
    }

}