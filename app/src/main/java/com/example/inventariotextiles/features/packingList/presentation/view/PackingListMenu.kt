package com.example.inventariotextiles.features.packingList.presentation.view

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.inventariotextiles.features.packingList.presentation.viewModel.PackingListViewModel
import com.example.inventariotextiles.presentation.view.components.MainMenu

@Composable
fun PackingListMenu(
    navController: NavController,
    viewModel: PackingListViewModel = viewModel()
){
    MainMenu("Packing List", viewModel.menuItems, navController)
}