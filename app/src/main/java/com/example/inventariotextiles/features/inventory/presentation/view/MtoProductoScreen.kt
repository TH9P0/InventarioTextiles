// features/inventory/presentation/view/MtoProductsScreen.kt
package com.example.inventariotextiles.features.inventory.presentation.view

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.inventariotextiles.domain.model.Prenda
import com.example.inventariotextiles.features.inventory.presentation.viewModel.MtoProductoViewModel
import com.example.inventariotextiles.presentation.view.states.MtoProductsUIState
import java.util.UUID
import kotlinx.coroutines.delay
import androidx.navigation.NavController
import com.example.inventariotextiles.domain.model.Prenda
import com.example.inventariotextiles.features.inventory.presentation.viewmodel.MtoProductoViewModel
import com.example.inventariotextiles.features.inventory.presentation.states.MtoProductsUIState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MtoProductsScreen(
    navController: NavController,
    productId: String? = null
) {
    val context = LocalContext.current
    val viewModel: MtoProductoViewModel = viewModel(
            factory = object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MtoProductoViewModel(context.applicationContext as Application) as T
                }
            }
        )

    val uiState = viewModel.uiState.collectAsState().value
    val errorMessage = viewModel.errorMessage.collectAsState().value

    var codigoBarras by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var talla by remember { mutableStateOf("") }
    var color by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }

    LaunchedEffect(productId) {
        productId?.let {
            delay(100) // Pequeño delay para evitar problemas de inicialización
                        viewModel.loadProduct(it)
        }
    }

    LaunchedEffect(uiState) {
        if (uiState is MtoProductsUIState.Editing) {
                    val producto = uiState.producto
                    codigoBarras = producto.codigoBarras
                    nombre = producto.nombre
                    talla = producto.talla
                    color = producto.color
                    descripcion = producto.descripcion ?: ""
                }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (productId == null) "Nueva Prenda" else "Editar Prenda") }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    val prenda = Prenda(
                                            id = if (productId == null) UUID.randomUUID().toString() else productId,
                                            codigoBarras = codigoBarras,
                                            nombre = nombre,
                                            talla = talla,
                                            color = color,
                                            descripcion = descripcion.ifBlank { null },
                                            imagen = null // Ahora siempre es null
                                        )
                                        viewModel.saveProduct(prenda)
                    viewModel.saveProduct(prenda)
                }
            ) {
                Icon(Icons.Default.Save, contentDescription = "Guardar")
                Text("Guardar")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            when (uiState) {
                            MtoProductsUIState.Loading -> {
                                CircularProgressIndicator(Modifier.fillMaxWidth().padding(16.dp))
                            }
                            is MtoProductsUIState.Editing, MtoProductsUIState.Creating -> {
                                OutlinedTextField(
                                    value = codigoBarras,
                                    onValueChange = { codigoBarras = it },
                                    label = { Text("Código de Barras") },
                                    modifier = Modifier.fillMaxWidth(),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                                )
                                Spacer(modifier = Modifier.height(8.dp))

                                OutlinedTextField(
                                    value = nombre,
                                    onValueChange = { nombre = it },
                                    label = { Text("Nombre") },
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(8.dp))

                                OutlinedTextField(
                                    value = talla,
                                    onValueChange = { talla = it },
                                    label = { Text("Talla") },
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(8.dp))

                                OutlinedTextField(
                                    value = color,
                                    onValueChange = { color = it },
                                    label = { Text("Color") },
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(8.dp))

                                OutlinedTextField(
                                    value = descripcion,
                                    onValueChange = { descripcion = it },
                                    label = { Text("Descripción") },
                                    modifier = Modifier.fillMaxWidth(),
                                    maxLines = 3
                                )
                            }
                            MtoProductsUIState.Success -> {
                                Text("¡Guardado exitosamente!", style = MaterialTheme.typography.headlineMedium)
                                LaunchedEffect(Unit) {
                                    navController.popBackStack()
                                }
                            }
                            is MtoProductsUIState.Error -> {
                                Text((uiState as MtoProductsUIState.Error).message, color = MaterialTheme.colorScheme.error)
                            }
                        }

                        errorMessage?.let {
                            Text(it, color = MaterialTheme.colorScheme.error)
                            LaunchedEffect(Unit) {
                                viewModel.clearErrorMessage()
                            }
                        }

            errorMessage?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
                LaunchedEffect(Unit) {
                    viewModel.clearErrorMessage()
                }
            }
        }
    }
}