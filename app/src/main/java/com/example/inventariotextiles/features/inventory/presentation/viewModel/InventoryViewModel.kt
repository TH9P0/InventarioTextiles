package com.example.inventariotextiles.features.inventory.presentation.viewModel

import androidx.lifecycle.ViewModel

class InventoryViewModel:ViewModel() {
    val menuItems = listOf(
        "Consultar" to "PantallaConsultar",
        "Modificar" to "PantallaModificar",
        "Reportes" to "PantallaReportes"
    )
}