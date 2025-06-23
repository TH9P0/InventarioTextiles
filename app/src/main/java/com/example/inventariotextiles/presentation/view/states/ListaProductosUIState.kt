package com.example.inventariotextiles.presentation.view.states

import com.example.inventariotextiles.domain.model.Prenda
import com.example.inventariotextiles.domain.model.Producto

sealed class ListaProductosUIState {
    object Loading : ListaProductosUIState()
    data class Success(val data: List<Prenda>) : ListaProductosUIState()
    data class Error(val message: String) : ListaProductosUIState()
}