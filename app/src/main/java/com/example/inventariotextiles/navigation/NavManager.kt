package com.example.inventariotextiles.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.inventariotextiles.features.audit.presentation.view.AuditMenu
import com.example.inventariotextiles.features.container.presentation.view.ContainerMenu
import com.example.inventariotextiles.features.inventory.presentation.view.InventoryMenu
import com.example.inventariotextiles.features.packingList.presentation.view.PackingListMenu
import com.example.inventariotextiles.features.transfer.presentation.view.TransferMenu
import com.example.inventariotextiles.presentation.view.LoginScreen
import com.example.inventariotextiles.presentation.view.components.MainMenu

@Composable
fun NavManager() {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = "Login")
    {
        composable("Login"){
            LoginScreen(context, navController)
        }

        composable("MainMenu") {
            MainMenu(
                "Menu Principal",
                menuItems = listOf(
                    "Inventario" to "InventoryMenu",
                    "Contenedores" to "ContainerMenu",
                    "Transferir" to "TransferMenu",
                    "Packing List" to "PackingListMenu",
                    "Auditoría" to "AuditMenu",
                    "Usuarios" to "UserMenu"
                ),
                navController)
        }

        composable("InventoryMenu"){
            InventoryMenu(navController)
        }

        composable("ContainerMenu"){
            ContainerMenu(navController)
        }

        composable("TransferMenu"){
            TransferMenu(navController)
        }

        composable("PackingListMenu"){
            PackingListMenu(navController)
        }

        composable("AuditMenu"){
            AuditMenu(navController)
        }

        composable("UserMenu"){

        }

    }
}