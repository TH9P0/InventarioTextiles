package com.example.inventariotextiles.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPackingList(navControlador: NavController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerPackingContent(
                onItemSelected = { route ->
                    scope.launch { drawerState.close() }
                    navControlador.navigate(route)
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Packing List") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { padding ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
                    .background(color = Color(0xFFF8F5F0))
            ) {
                GridPackingButtons(navControlador)
            }
        }
    }
}

@Composable
fun DrawerPackingContent(onItemSelected: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp)
            .background(color = Color(0xFFF8F5F0))
    ) {
        val items = listOf(
            "Inventario" to "inventario",
            "Contenedores" to "contenedores",
            "Transferir" to "transferir",
            "Packing List" to "packing",
            "Auditoría" to "auditoria",
            "Usuarios" to "usuarios"
        )

        items.forEach { (title, route) ->
            TextButton(onClick = { onItemSelected(route) }) {
                Text(text = title)
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { /* preferencias */ }) {
            Icon(Icons.Default.Settings, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Preferencias")
        }
        TextButton(onClick = { /* cerrar sesión */ }) {
            Icon(Icons.Default.Info, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Cerrar Sesión")
        }
    }
}

@Composable
fun GridPackingButtons(navControlador: NavController) {
    LazyColumn {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                MainPackingCardButton("Crear") { navControlador.navigate("crear") }
                MainPackingCardButton("Historico") { navControlador.navigate("historico") }
            }
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                MainPackingCardButton("Consultar") { navControlador.navigate("consultar") }
                MainPackingCardButton("Reportes") { navControlador.navigate("reportes") }
            }
        }
    }
}

@Composable
fun MainPackingCardButton(title: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .size(140.dp)
            .background(color = Color(0xFF1C2D3C)),
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
