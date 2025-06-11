package com.example.inventariotextiles.presentation.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.example.inventariotextiles.presentation.viewModel.MtoProductoViewModel
import kotlinx.coroutines.launch
import kotlin.text.isNullOrEmpty

@Composable
fun MtoProductoScreen(viewModel: MtoProductoViewModel){
    Box(Modifier
        .fillMaxSize()
        .padding(16.dp)){
        ProductoDetallado(Modifier.fillMaxWidth(), viewModel)
    }
}

@Composable
fun ProductoDetallado(modifier: Modifier, viewModel: MtoProductoViewModel) {

    val nombre: String = viewModel.nombre.collectAsState().value
    val precio: String = viewModel.precio.collectAsState().value
    val descripcion: String = viewModel.descripcion.collectAsState().value
    val botonEnabled: Boolean = viewModel.botonEnabled.collectAsState().value
    val isLoading: Boolean = viewModel.isLoading.collectAsState().value
    val coroutineScope = rememberCoroutineScope()

    if(isLoading){
        Box(Modifier.fillMaxSize()){
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    }else{

        Column (modifier){
            BarraSuperior(null)
            Spacer(Modifier.height(8.dp))
            Id(null)
            Spacer(Modifier.height(8.dp))
            NombreProducto(nombre) { viewModel.onProductoChanged(it, precio, descripcion) }
            Spacer(Modifier.height(8.dp))
            PrecioProducto(precio) { viewModel.onProductoChanged(nombre, it, descripcion) }
            Spacer(Modifier.height(8.dp))
            DescripcionProducto(descripcion) { viewModel.onProductoChanged(nombre, precio, it) }
            Spacer(Modifier.height(8.dp))
            Text("Los campos con un \"*\" son obligatorios.",  textAlign = TextAlign.Center, fontSize = 24.sp)
            Spacer(Modifier.height(8.dp))
            BotonAccion(botonEnabled) {
                coroutineScope.launch {
                    viewModel.onProductSelected()
                }
            }
        }

    }
}

@Composable
fun BarraSuperior(id: String?) {
    Box(modifier = Modifier.fillMaxWidth()) {
        IconButton(
            onClick = {},
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Regresar",
                modifier = Modifier.size(24.dp)
            )
        }

        Text(
            text = if (id != null) "Editar Producto" else "Agregar Producto",
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.headlineMedium,
            fontSize = 24.sp
        )
    }
}

@Composable
fun Id(id: String?) {
    if (!id.isNullOrEmpty()) {
        Text("ID: $id", textAlign = TextAlign.Center, fontSize = 20.sp, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
    }
}

@Composable
fun NombreProducto(
    nombre: String,
    onProductoChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = nombre,
        onValueChange = { onProductoChanged(it) },
        label = { Text("Nombre *") },
        modifier = Modifier.fillMaxWidth()
    )
}


@Composable
fun PrecioProducto(
    precio: String,
    onProductoChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = precio,
        onValueChange = { onProductoChanged(it) },
        label = { Text("Precio *") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        singleLine = true
    )
}

@Composable
fun DescripcionProducto(
    descripcion: String?,
    onProductoChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = descripcion ?: "",
        onValueChange = { onProductoChanged(it) },
        label = { Text("Descripción") },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun BotonAccion(
    loginEnabled: Boolean,
    onProductSelected: () -> Unit
) {
    Button(
        onClick = { onProductSelected() },
        enabled = loginEnabled
    ) {
        Text("Guardar Datos")
    }
}
