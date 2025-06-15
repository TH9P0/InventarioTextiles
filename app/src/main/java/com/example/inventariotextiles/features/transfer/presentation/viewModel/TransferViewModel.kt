package com.example.inventariotextiles.features.transfer.presentation.viewModel

import androidx.lifecycle.ViewModel

class TransferViewModel:ViewModel() {
    val menuItems = listOf(
        "Mercancia" to "PantallaMercancia",
        "Historico" to "PantallaHistorico",
        "Reportes" to "PantallaReportes"
    )
}