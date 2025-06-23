package com.example.inventariotextiles.features.inventory.presentation.viewModel

import androidx.lifecycle.ViewModel

class InventoryViewModel:ViewModel() {
    val menuItems = listOf(
        "Consultar" to "ListaProductosScreen",
        "Modificar" to "MtoProductoScreen",
        "Reportes" to "PantallaReportes"
    )
}