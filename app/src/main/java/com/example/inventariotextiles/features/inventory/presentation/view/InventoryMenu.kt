package com.example.inventariotextiles.features.inventory.presentation.view

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.inventariotextiles.features.inventory.presentation.viewModel.InventoryViewModel
import com.example.inventariotextiles.presentation.view.components.MainMenu

@Composable
fun InventoryMenu(
    navController: NavController,
    viewModel: InventoryViewModel = viewModel()
){
    MainMenu("Inventario", viewModel.menuItems, navController)
}