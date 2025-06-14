package com.example.inventariotextiles.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaCrearPackingList(navControlador: NavController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerPackingContent(
                scope = scope,
                drawerState = drawerState,
                onItemSelected = { route ->
                    navControlador.navigate(route)
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Crear Packing List") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { padding ->
            FormularioPackingList(Modifier.padding(padding))
        }
    }
}

@Composable
fun DrawerPackingContent(
    scope: CoroutineScope,
    drawerState: DrawerState,
    onItemSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp)
            .background(color = Color(0xFFF8F5F0))
    ) {
        val items = listOf(
            "Inventario" to "PantallaAlmacen",
            "Contenedores" to "PantallaContenedores",
            "Transferir" to "PantallaTransferir",
            "Packing List" to "PantallaPacking",
            "Auditoría" to "PantallaAuditoria",
            "Usuarios" to "PantallaUsuarios"
        )

        items.forEach { (title, route) ->
            TextButton(onClick = {
                scope.launch { drawerState.close() }
                onItemSelected(route)
            }) {
                Text(text = title)
            }
        }
    }
}

@Composable
fun FormularioPackingList(modifier: Modifier = Modifier) {
    var folio by remember { mutableStateOf("") }
    var quienEnvia by remember { mutableStateOf("") }
    var quienRecibe by remember { mutableStateOf("") }
    var idContenedor by remember { mutableStateOf("") }
    var cantidadPrenda by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFF8F5F0)),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = folio,
            onValueChange = { folio = it },
            label = { Text("Folio") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = quienEnvia,
            onValueChange = { quienEnvia = it },
            label = { Text("Quién Envia") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = quienRecibe,
            onValueChange = { quienRecibe = it },
            label = { Text("Quién Recibe") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = idContenedor,
            onValueChange = { if (it.all { char -> char.isDigit() }) idContenedor = it },
            label = { Text("ID Contenedor") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Divider(thickness = 1.dp, color = Color.Gray)

        Text("Agregar Prendas", style = MaterialTheme.typography.titleMedium)

        OutlinedTextField(
            value = cantidadPrenda,
            onValueChange = { if (it.all { char -> char.isDigit() }) cantidadPrenda = it },
            label = { Text("Cantidad") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { /* Aquí después metemos la lógica de agregar prendas */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar Prenda")
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { /* Aquí irá la lógica de guardar el Packing List */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1C2D3C))
        ) {
            Text("Guardar", color = Color.White)
        }
    }
}
