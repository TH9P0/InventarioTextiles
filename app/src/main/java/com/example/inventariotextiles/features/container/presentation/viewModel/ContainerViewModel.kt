package com.example.inventariotextiles.features.container.presentation.viewModel

import androidx.lifecycle.ViewModel

class ContainerViewModel: ViewModel() {
    val menuItems = listOf(
        "Recibir" to "PantallaRecibir",
        "Consultar" to "PantallaConsultar",
        "Historico" to "PantallaHistorico",
        "Reportes" to "PantallaReportes"
    )
}