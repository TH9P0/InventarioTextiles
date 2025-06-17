package com.example.inventariotextiles.features.transfer.presentation.view

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.inventariotextiles.features.transfer.presentation.viewModel.TransferViewModel
import com.example.inventariotextiles.presentation.view.components.MainMenu

@Composable
fun TransferMenu(
    navController: NavController,
    viewModel: TransferViewModel = viewModel()
){
    MainMenu("Transferencias", viewModel.menuItems, navController)
}