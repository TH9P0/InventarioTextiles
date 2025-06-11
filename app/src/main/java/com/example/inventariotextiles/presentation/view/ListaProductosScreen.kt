package com.example.inventariotextiles.presentation.view

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.inventariotextiles.domain.model.Producto
import com.example.inventariotextiles.presentation.view.components.ProductCard
import com.example.inventariotextiles.presentation.view.states.ListaProductosUIState
import com.example.inventariotextiles.presentation.viewModel.ListaProductosViewModel
import com.example.inventariotextiles.ui.theme.MtoProductoTheme
import kotlin.let

@Composable
fun ListaProductosScreen(navController: NavController, context: Context){
    val viewModel: ListaProductosViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ListaProductosViewModel(context) as T
            }
        }
    )

    val uiState = viewModel.uiState.collectAsState().value
    val errorMessage = viewModel.errorMessage.collectAsState().value

    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            viewModel.clearErrorMessage()
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        when (val state = uiState) {
            is ListaProductosUIState.Loading -> {
                CircularProgressIndicator(Modifier.padding(paddingValues))
            }
            is ListaProductosUIState.Success -> {
                ListaProductos(
                    productList = state.productList,
                    navController = navController,
                    onDelete = { productId -> viewModel.deleteProduct(productId) },
                    modifier = Modifier.padding(paddingValues)
                )
            }
            is ListaProductosUIState.Error -> {
                // Mostrar estado de error
                Text(
                    text = state.message,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun ListaProductos(productList: List<Producto>, navController: NavController, onDelete: (String) -> Unit, modifier: Modifier) {
    LazyColumn(modifier.fillMaxWidth().systemBarsPadding().padding(8.dp)) {
        items(productList.size) { index ->
            ProductCard(product = productList[index], navController, onDelete = {onDelete(productList[index].id)})
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListaProductosScreenPreview() {
    MtoProductoTheme {
        val sampleProducts = listOf(
            Producto("1", "Zapatos", "45.99", "Zapatos deportivos"),
            Producto("2", "Camiseta", "19.99", "Camiseta de algodón")
        )

        ListaProductos(
            productList = sampleProducts,
            navController = rememberNavController(),
            onDelete = {},
            modifier = Modifier
        )
    }
}