package com.example.inventariotextiles.features.audit.presentation.view

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.inventariotextiles.features.audit.presentation.viewModel.AuditViewModel
import com.example.inventariotextiles.presentation.view.components.MainMenu

@Composable
fun AuditMenu(
    navController: NavController,
    viewModel: AuditViewModel = viewModel()
){
    MainMenu("Auditoria", viewModel.menuItems, navController)
}