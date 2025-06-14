package com.example.inventariotextiles.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaTransferir(navControlador: NavController) {
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
                    title = { Text("Transferir") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { padding ->
            FormularioTransferencia(Modifier.padding(padding))
        }
    }
}

@Composable
fun FormularioTransferencia(modifier: Modifier = Modifier) {
    // Lista de almacenes de ejemplo
    val almacenes = listOf("Almacén A", "Almacén B", "Almacén C")

    var almacenOrigen by remember { mutableStateOf(almacenes.first()) }
    var almacenDestino by remember { mutableStateOf("") }
    var cantidadPrenda by remember { mutableStateOf("") }

    val almacenesDestino = almacenes.filter { it != almacenOrigen }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFF8F5F0)),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Selecciona almacenes:", style = MaterialTheme.typography.titleMedium)

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            // Almacén origen (dropdown)
            AlmacenDropdown(
                label = "Origen",
                almacenes = almacenes,
                seleccionado = almacenOrigen,
                onSeleccionado = { almacenOrigen = it }
            )

            Text(" → ", modifier = Modifier.align(Alignment.CenterVertically))

            // Almacén destino (dropdown)
            AlmacenDropdown(
                label = "Destino",
                almacenes = almacenesDestino,
                seleccionado = almacenDestino,
                onSeleccionado = { almacenDestino = it }
            )
        }

        Divider(thickness = 1.dp, color = Color.Gray)

        Text("Agregar Prendas", style = MaterialTheme.typography.titleMedium)

        OutlinedTextField(
            value = cantidadPrenda,
            onValueChange = {
                if (it.all { char -> char.isDigit() }) {
                    cantidadPrenda = it
                }
            },
            label = { Text("Cantidad") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            isError = cantidadPrenda.toIntOrNull()?.let { it <= 0 } == true
        )

        Button(
            onClick = {
                // Aquí va la lógica para agregar la prenda (validación simple)
                if (cantidadPrenda.isNotEmpty() && cantidadPrenda.toInt() > 0) {
                    // lógica de agregar
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = cantidadPrenda.isNotEmpty() && cantidadPrenda.toIntOrNull()?.let { it > 0 } == true
        ) {
            Text("Agregar Prenda")
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { /* Guardar la transferencia */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1C2D3C))
        ) {
            Text("Guardar", color = Color.White)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlmacenDropdown(
    label: String,
    almacenes: List<String>,
    seleccionado: String,
    onSeleccionado: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = seleccionado,
            onValueChange = {},
            label = { Text(label) },
            readOnly = true,
            modifier = Modifier.menuAnchor().width(150.dp)
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            almacenes.forEach { almacen ->
                DropdownMenuItem(
                    text = { Text(almacen) },
                    onClick = {
                        onSeleccionado(almacen)
                        expanded = false
                    }
                )
            }
        }
    }
}
