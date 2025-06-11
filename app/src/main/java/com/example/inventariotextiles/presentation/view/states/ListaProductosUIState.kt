package com.example.inventariotextiles.presentation.view.states

import com.example.inventariotextiles.domain.model.Producto

sealed class ListaProductosUIState {
    object Loading: ListaProductosUIState()
    data class  Success (val productList: List<Producto>): ListaProductosUIState()
    data class Error (val message: String): ListaProductosUIState()
}