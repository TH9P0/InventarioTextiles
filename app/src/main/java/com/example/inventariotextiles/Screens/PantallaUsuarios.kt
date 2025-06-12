package com.example.inventariotextiles.Screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

data class Usuario(val nombre: String, val rol: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaUsuarios(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Usuarios") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .background(Color(0xFFF8F5F0))
        ) {
            ListaUsuarios(
                usuarios = listOf(
                    Usuario("Juan Perez", "Almacenista"),
                    Usuario("María López", "Capturista"),
                    Usuario("Pedro Sánchez", "Supervisor"),
                    Usuario("Ana Gómez", "Auditor")
                )
            )
        }
    }
}

@Composable
fun ListaUsuarios(usuarios: List<Usuario>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(usuarios.size) { index ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Usuario",
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(text = usuarios[index].nombre, style = MaterialTheme.typography.titleMedium)
                        Text(text = usuarios[index].rol, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}
