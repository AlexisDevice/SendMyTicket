package com.ucne.sendmyticket.ui.SuplidoresGastos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ucne.sendmyticket.data.remote.dto.SuplidoresDto

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun Registrar(viewModel: SuplidoresViewModel = hiltViewModel()) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(8.dp)
        ) {

            Text(
                text = "Registro de Suplidores",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                fontWeight = FontWeight.Bold
            )

            Row(Modifier.fillMaxWidth()) {
                Column(Modifier.weight(1f)) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = viewModel.idSuplidor.toString(),
                        label = { Text(text = "IdSuplidor") },
                        singleLine = true,
                        onValueChange = {
                            val newValue = it.toIntOrNull()
                            if (newValue != null) {
                                viewModel.idSuplidor = newValue
                            }
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Number
                        )
                    )
                    CustomOutlinedTextField(
                        value = viewModel.nombres,
                        onValueChange = { viewModel.nombres = it },
                        label = "Nombres",
                        isError = viewModel.isValidNombres,
                        imeAction = ImeAction.Next
                    )
                    CustomOutlinedTextField(
                        value = viewModel.direccion,
                        onValueChange = { viewModel.direccion = it },
                        label = "Dirección",
                        isError = viewModel.isValidDireccion,
                        imeAction = ImeAction.Next
                    )
                    CustomOutlinedTextField(
                        value = viewModel.telefono,
                        onValueChange = { viewModel.telefono = it },
                        label = "Teléfono",
                        isError = viewModel.isValidTelefono,
                        imeAction = ImeAction.Next
                    )
                }
                Column(Modifier.weight(1f)) {
                    CustomOutlinedTextField(
                        value = viewModel.celular,
                        onValueChange = { viewModel.celular = it },
                        label = "Celular",
                        isError = viewModel.isValidCelular,
                        imeAction = ImeAction.Next
                    )
                    CustomOutlinedTextField(
                        value = viewModel.fax,
                        onValueChange = { viewModel.fax = it },
                        label = "Fax",
                        isError = viewModel.isValidFax,
                        imeAction = ImeAction.Next
                    )
                    CustomOutlinedTextField(
                        value = viewModel.rnc,
                        onValueChange = { viewModel.rnc = it },
                        label = "Rnc",
                        isError = viewModel.isValidRnc,
                        imeAction = ImeAction.Next
                    )
                }
            }

            Row(Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = viewModel.tipoNcf.toString(),
                    label = { Text(text = "Tipo Ncf") },
                    singleLine = true,
                    onValueChange = {
                        val newValue = it.toIntOrNull()
                        if (newValue != null) {
                            viewModel.tipoNcf = newValue
                        }
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number
                    )
                )
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = viewModel.condicion.toString(),
                    label = { Text(text = "Condición") },
                    singleLine = true,
                    onValueChange = {
                        val newValue = it.toIntOrNull()
                        if (newValue != null) {
                            viewModel.condicion = newValue
                        }
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number
                    )
                )
            }

            CustomOutlinedTextField(
                value = viewModel.email,
                onValueChange = { viewModel.email = it },
                label = "Email",
                isError = viewModel.isValidEmail,
                imeAction = ImeAction.Next
            )

            OutlinedButton(
                onClick = {
                    keyboardController?.hide()
                    if (viewModel.isValid()) {
                        viewModel.guardarSuplidor()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Guardar")
                Icon(imageVector = Icons.Default.Save, contentDescription = "Guardar")
            }

            uiState.suplidores?.let { suplidor -> Consulta(suplidor) }
        }
    }
}


@Composable
fun Consulta(suplidores: List<SuplidoresDto>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Lista de suplidores", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(suplidores) { supply ->
                SuplidoresItem(supply)
            }
        }
    }
}

@Composable
fun SuplidoresItem(suplidores: SuplidoresDto) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "ID: ${suplidores.idSuplidor}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Nombres: ${suplidores.nombres}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Rnc: ${suplidores.rnc}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Dirección: ${suplidores.direccion}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Teléfono: ${suplidores.telefono}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Celular: ${suplidores.celular}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Fax: ${suplidores.fax}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Tipo Ncf: ${suplidores.tipoNcf.toString()}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Condición: ${suplidores.condicion.toString()}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Email: ${suplidores.email}", style = MaterialTheme.typography.titleMedium)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean,
    imeAction: ImeAction
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = label) },
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = if (isError) Color.Gray else Color.Red,
            unfocusedBorderColor = if (isError) Color.Gray else Color.Red
        ),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = imeAction)
    )
}