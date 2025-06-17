package com.example.inventariotextiles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.inventariotextiles.navigation.NavManager
import com.example.inventariotextiles.ui.theme.InventarioTextilesTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InventarioTextilesTheme {
                NavManager()
            }
        }
    }
}