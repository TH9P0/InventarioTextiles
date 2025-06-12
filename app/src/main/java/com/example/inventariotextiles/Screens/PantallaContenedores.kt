package com.example.inventariotextiles.Screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaContenedores(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Contenedores") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .background(Color(0xFFF8F5F0)),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                BotonContenedor("Recibir") { navController.navigate("recibir") }
                BotonContenedor("Consultar") { navController.navigate("consultar") }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                BotonContenedor("Histórico") { navController.navigate("historico") }
                BotonContenedor("Reportes") { navController.navigate("reportes") }
            }
        }
    }
}

@Composable
fun BotonContenedor(titulo: String, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .size(140.dp)
            .background(Color(0xFF1C2D3C)),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(Icons.Default.Inventory, contentDescription = titulo, modifier = Modifier.size(48.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(titulo)
        }
    }
}
