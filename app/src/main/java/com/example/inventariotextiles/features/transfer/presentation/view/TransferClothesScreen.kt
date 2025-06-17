package com.example.inventariotextiles.features.transfer.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun TransferClothes(navControlador: NavController) {
    // Lista de almacenes de ejemplo
    val almacenes = listOf("Almacén A", "Almacén B", "Almacén C")

    var almacenOrigen by remember { mutableStateOf(almacenes.first()) }
    var almacenDestino by remember { mutableStateOf("") }
    var cantidadPrenda by remember { mutableStateOf("") }

    val almacenesDestino = almacenes.filter { it != almacenOrigen }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row (Modifier.fillMaxWidth().padding(8.dp)){
            IconButton(onClick = {navControlador.navigate("TransferMenu")}) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Regresar", tint = MaterialTheme.colorScheme.onSurface)
            }

            Text("Transferir Prendas", modifier = Modifier.align(Alignment.CenterVertically), color = MaterialTheme.colorScheme.onSurface)
        }

        Text("Selecciona almacenes:", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            // Almacén origen (dropdown)
            AlmacenDropdown(
                label = "Origen",
                almacenes = almacenes,
                seleccionado = almacenOrigen,
                onSeleccionado = { almacenOrigen = it }
            )

            Text(" → ", modifier = Modifier.align(Alignment.CenterVertically), color = MaterialTheme.colorScheme.onSurface)

            // Almacén destino (dropdown)
            AlmacenDropdown(
                label = "Destino",
                almacenes = almacenesDestino,
                seleccionado = almacenDestino,
                onSeleccionado = { almacenDestino = it }
            )
        }

        HorizontalDivider(thickness = 1.dp, color = Color.Gray)

        Text("Agregar Prendas", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)

        OutlinedTextField(
            value = cantidadPrenda,
            onValueChange = {
                if (it.all { char -> char.isDigit() }) {
                    cantidadPrenda = it
                }
            },
            label = { Text("Cantidad", color = MaterialTheme.colorScheme.onSurface) },
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
                    text = { Text(almacen, color = MaterialTheme.colorScheme.onSurface) },
                    onClick = {
                        onSeleccionado(almacen)
                        expanded = false
                    }
                )
            }
        }
    }
}