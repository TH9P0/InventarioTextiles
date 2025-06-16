package com.example.inventariotextiles.features.packingList.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
fun CreatePackingList(navControlador: NavController) {
    var folio by remember { mutableStateOf("") }
    var quienEnvia by remember { mutableStateOf("") }
    var quienRecibe by remember { mutableStateOf("") }
    var idContenedor by remember { mutableStateOf("") }
    var cantidadPrenda by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(Modifier.fillMaxWidth().padding(8.dp)){
            IconButton(onClick = {navControlador.navigate("PackingListMenu")}) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Regresar", tint = MaterialTheme.colorScheme.onSurface)
            }
            Text("Crear packing list", modifier = Modifier.align(Alignment.CenterVertically), color = MaterialTheme.colorScheme.onSurface)
        }

        OutlinedTextField(
            value = folio,
            onValueChange = { folio = it },
            label = { Text("Folio", color = MaterialTheme.colorScheme.onSurface) },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = quienEnvia,
            onValueChange = { quienEnvia = it },
            label = { Text("Quién Envia", color = MaterialTheme.colorScheme.onSurface) },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = quienRecibe,
            onValueChange = { quienRecibe = it },
            label = { Text("Quién Recibe", color = MaterialTheme.colorScheme.onSurface) },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = idContenedor,
            onValueChange = { if (it.all { char -> char.isDigit() }) idContenedor = it },
            label = { Text("ID Contenedor", color = MaterialTheme.colorScheme.onSurface) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        HorizontalDivider(thickness = 1.dp, color = Color.Gray)

        Text("Agregar Prendas", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)

        OutlinedTextField(
            value = cantidadPrenda,
            onValueChange = { if (it.all { char -> char.isDigit() }) cantidadPrenda = it },
            label = { Text("Cantidad", color = MaterialTheme.colorScheme.onSurface) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { /* Aquí después metemos la lógica de agregar prendas */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar Prenda", color = MaterialTheme.colorScheme.onSurface)
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { /* Aquí irá la lógica de guardar el Packing List */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1C2D3C))
        ) {
            Text("Guardar", color = MaterialTheme.colorScheme.onSurface)
        }
    }
}