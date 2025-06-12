package com.example.inventariotextiles.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAuditoria(navControlador: NavController) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Auditoría") }
            )
        }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .background(color = androidx.compose.ui.graphics.Color(0xFFF8F5F0))
        ) {
            GridAuditoriaButtons(navControlador)
        }
    }
}

@Composable
fun GridAuditoriaButtons(navControlador: NavController) {
    LazyColumn {
        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                MainAuditoriaCardButton("Planeada", onClick = { navControlador.navigate("planeada") })
                MainAuditoriaCardButton("No Planeada", onClick = { navControlador.navigate("noPlaneada") })
            }
        }
        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                MainAuditoriaCardButton("Historial", onClick = { navControlador.navigate("historial") })
                MainAuditoriaCardButton("Planificar", onClick = { navControlador.navigate("planificar") })
            }
        }
        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                MainAuditoriaCardButton("Reportes", onClick = { navControlador.navigate("reportes") })
            }
        }
    }
}

@Composable
fun MainAuditoriaCardButton(title: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .size(140.dp)
            .background(color = androidx.compose.ui.graphics.Color(0xFF1C2D3C)),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(Icons.Default.Image, contentDescription = title, modifier = Modifier.size(48.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(title)
        }
    }
}
