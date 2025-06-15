package com.example.inventariotextiles.features.container.presentation.view

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.inventariotextiles.features.container.presentation.viewModel.ContainerViewModel
import com.example.inventariotextiles.presentation.view.components.MainMenu

@Composable
fun ContainerMenu(
    navController: NavController,
    viewModel: ContainerViewModel = viewModel()
){
    MainMenu("Contenedores", viewModel.menuItems, navController)
}