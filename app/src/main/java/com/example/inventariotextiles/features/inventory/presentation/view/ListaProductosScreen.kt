package com.example.inventariotextiles.features.inventory.presentation.view

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.inventariotextiles.domain.model.Prenda
import com.example.inventariotextiles.presentation.view.components.PrendaCard
import com.example.inventariotextiles.presentation.view.states.ListaProductosUIState
import com.example.inventariotextiles.features.inventory.presentation.viewModel.ListaProductosViewModel
import com.example.inventariotextiles.ui.theme.InventarioTextilesTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaProductosScreen(navController: NavController) {
    val context = LocalContext.current
    val viewModel: ListaProductosViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ListaProductosViewModel(context.applicationContext as Application) as T
            }
        }
    )

    // Cargar productos al iniciar
    LaunchedEffect(Unit) {
        viewModel.loadProductos()
    }

    val uiState = viewModel.uiState.collectAsState().value
    val errorMessage = viewModel.errorMessage.collectAsState().value
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Manejar mensajes de error
    LaunchedEffect(errorMessage) {
        errorMessage?.let { message ->
            scope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.clearErrorMessage()
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("MtoProductoScreen") }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar prenda")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (val state = uiState) {
                is ListaProductosUIState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }

                is ListaProductosUIState.Success -> {
                    if (state.data.isEmpty()) {
                        // Mostrar mensaje cuando no hay prendas
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                Icons.Outlined.Inventory2,
                                contentDescription = "Inventario vacío",
                                modifier = Modifier.size(64.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "No hay prendas registradas",
                                style = MaterialTheme.typography.titleMedium,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = { navController.navigate("MtoProductoScreen") }
                            ) {
                                Text("Agregar primera prenda")
                            }
                        }
                    } else {
                        ListaPrendas(
                            prendaList = state.data,
                            navController = navController,
                            onDelete = { prendaId -> viewModel.deletePrenda(prendaId) },
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                is ListaProductosUIState.Error -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Error: ${state.message}",
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { viewModel.loadProductos() }
                        ) {
                            Text("Reintentar")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ListaPrendas(
    prendaList: List<Prenda>,
    navController: NavController,
    onDelete: (String) -> Unit,
    modifier: Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        items(prendaList, key = { it.id }) { prenda ->
            PrendaCard(
                prenda = prenda,
                navController = navController,
                onDelete = { onDelete(prenda.id) }
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun ListaProductosScreenPreview() {
    InventarioTextilesTheme {
        val samplePrendas = listOf(
            Prenda("1", "123456", "Camiseta", "M", "Rojo", "Camiseta de algodón", null),
            Prenda("2", "789012", "Pantalón", "32", "Azul", "Pantalón vaquero", null)
        )

        Scaffold { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                ListaPrendas(
                    prendaList = samplePrendas,
                    navController = rememberNavController(),
                    onDelete = {},
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}