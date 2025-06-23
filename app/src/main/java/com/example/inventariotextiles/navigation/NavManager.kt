package com.example.inventariotextiles.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.inventariotextiles.features.audit.presentation.view.AuditMenu
import com.example.inventariotextiles.features.container.presentation.view.ContainerMenu
import com.example.inventariotextiles.features.inventory.presentation.view.InventoryMenu
import com.example.inventariotextiles.features.inventory.presentation.view.ListaProductosScreen
import com.example.inventariotextiles.features.inventory.presentation.view.MtoProductsScreen
import com.example.inventariotextiles.features.inventory.presentation.view.PantallaReportes
import com.example.inventariotextiles.features.packingList.presentation.view.CreatePackingList
import com.example.inventariotextiles.features.packingList.presentation.view.PackingListMenu
import com.example.inventariotextiles.features.transfer.presentation.view.TransferClothes
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

        // Navigation in Main Menu
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


        // Navigation in Inventory Menu
        composable("InventoryMenu"){
            InventoryMenu(navController)
        }
        composable("ListaProductosScreen") {
            ListaProductosScreen(navController = navController)
        }
        composable(
            "MtoProductoScreen/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")
            MtoProductsScreen(
                navController = navController,
                productId = productId  // Solo estos 2 parámetros
            )
        }

        composable("PantallaReportes") {
            // Asegúrate de crear esta pantalla
            PantallaReportes(navController)
        }


        // Navigation in Container Menu
        composable("ContainerMenu"){
            ContainerMenu(navController)
        }

        composable("ListaPrendas"){

        }

        // Navigation in Transfer Menu
        composable("TransferMenu"){
            TransferMenu(navController)
        }

        composable("Transferir"){
            TransferClothes(navController)
        }

        // Navigation on PackingListMenu
        composable("PackingListMenu"){
            PackingListMenu(navController)
        }

        composable("CreatePackingList"){
            CreatePackingList(navController)
        }

        // Navigation on Audit Menu
        composable("AuditMenu"){
            AuditMenu(navController)
        }

        // Navigation on User Menu
        composable("UserMenu"){

        }

    }
}