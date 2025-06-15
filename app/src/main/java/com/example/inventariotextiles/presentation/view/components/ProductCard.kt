package com.example.inventariotextiles.presentation.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.inventariotextiles.domain.model.Producto
import com.example.inventariotextiles.ui.theme.InventarioTextilesTheme
import kotlin.text.isEmpty

@Composable
fun ProductCard(
    product: Producto,
    navController: NavController,
    onDelete: (String) -> Unit)
{
    var showDialog by rememberSaveable { mutableStateOf(false) }

    Card (
        Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(8.dp)
    ){
        ProductContent(
            product = product,
            navController = navController,
            onDelete = onDelete,
            onShowDialogRequest = { showDialog = true}
        )
    }


    DeleteConfirmDialog(
        show = showDialog,
        onDismiss = { showDialog = false },
        onConfirm = {
            onDelete(product.id)
            showDialog = false
        }
    )
}

@Composable
fun ProductContent(
    product: Producto,
    navController: NavController,
    onDelete: (String) -> Unit,
    onShowDialogRequest: () -> Unit, ) {
    Row (Modifier
        .padding(16.dp)
        .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
        ProductInfo(modifier = Modifier.weight(1f),product = product)
        ProductAction(navController = navController, productId = product.id, onDelete = onDelete, onShowDialogRequest = onShowDialogRequest)
    }
}

@Composable
fun ProductInfo(modifier: Modifier, product: Producto) {
    Column (modifier){
        Text(product.name, maxLines = 2, overflow = TextOverflow.Ellipsis, style = MaterialTheme.typography.titleMedium)
        Text(
            text = if (product.description.isEmpty()) "Sin descripcion" else product.description,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium
        )
        Text("$${product.price}", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
fun ProductAction(
    navController: NavController,
    productId: String,
    onDelete: (String) -> Unit,
    onShowDialogRequest: () -> Unit
) {
    Column {
        IconButton(onClick = { navController.navigate("ProductoMto?id=${productId}") }) {
            Icon(
                Icons.Filled.Edit,
                contentDescription = "Editar"
            )
        }
        IconButton(
            onClick = { onShowDialogRequest() } )
        {
            Icon(
                Icons.Filled.Delete,
                contentDescription = "Eliminar",
                tint = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
fun DeleteConfirmDialog(show: Boolean,onDismiss:() -> Unit, onConfirm:()-> Unit){
    if(show){
        AlertDialog (
            onDismissRequest = onDismiss,
            title = { Text("Eliminar producto?") },
            text = { Text("¿Estás seguro de que quieres eliminar este producto? Esta acción no se puede deshacer.") },
            confirmButton = { TextButton(onClick = onConfirm){Text("Eliminar", color = MaterialTheme.colorScheme.error)} },
            dismissButton = { TextButton(onClick = onDismiss){Text("Descartar")} }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProductCardPreview() {
    val producto = Producto(
        id = "0",
        name = "Nike Jogger",
        price = "18.65",
        description = "Sin Descripcion"
    )

    InventarioTextilesTheme { ProductCard(product = producto, navController = rememberNavController()){} }
}