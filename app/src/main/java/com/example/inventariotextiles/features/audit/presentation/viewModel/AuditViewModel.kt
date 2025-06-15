package com.example.inventariotextiles.features.audit.presentation.viewModel

import androidx.lifecycle.ViewModel

class AuditViewModel: ViewModel() {
    val menuItems = listOf(
        "Planeada" to "PantallaRecibir",
        "No Planeada" to "PantallaConsultar",
        "Historico" to "PantallaHistorico",
        "Planificar" to "PantallaReportes",
        "Reportes" to "PantallaReportes"
    )
}