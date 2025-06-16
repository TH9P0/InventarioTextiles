package com.example.inventariotextiles.features.packingList.presentation.viewModel

import androidx.lifecycle.ViewModel

class PackingListViewModel:ViewModel() {
    val menuItems = listOf(
        "Crear" to "CreatePackingList",
        "Historico" to "PantallaConsultar",
        "Consultar" to "PantallaHistorico",
        "Reportes" to "PantallaReportes"
    )
}