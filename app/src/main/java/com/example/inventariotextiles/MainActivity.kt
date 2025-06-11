package com.example.inventariotextiles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.inventariotextiles.presentation.view.ListaProductosScreen
import com.example.inventariotextiles.ui.theme.MtoProductoTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MtoProductoTheme {
                ListaProductosScreen(navController = rememberNavController(), LocalContext.current)
            }
        }
    }
}