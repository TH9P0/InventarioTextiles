package com.example.inventariotextiles.Screens


import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.inventariotextiles.Producto
import com.example.inventariotextiles.ui.theme.MtoProductoTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UIPrincipal(navControlador: NavController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    /*
       LaunchedEffect(Unit) {
         auxSQLite.readableDatabase.use { db ->
             db.rawQuery("SELECT * FROM producto;", null).use { cursor ->
                 val tempList = mutableListOf<Producto>()
                 while (cursor.moveToNext()) {
                     tempList.add(
                         Producto(
                             id = cursor.getString(0),
                             name = cursor.getString(1),
                             price = cursor.getString(2),
                             description = cursor.getString(3) ?: "Sin descripción",
                             imagen = cursor.getString(4) ?: ""
                         )
                     )
                 }
                 productList.clear()
                 productList.addAll(tempList)
             }
         }
     } */


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
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
                    title = { Text("UI Principal") },
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
            ) {
                GridButtons(navControlador)
            }
        }
    }
}

@Composable
fun DrawerContent(onItemSelected: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(Color(0xFFE3F2FD))
            .padding(16.dp)
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
fun GridButtons(navControlador: NavController) {
    LazyColumn {
        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                MainCardButton("Contenedores", onClick = { navControlador.navigate("PantallaContenedores") })
                MainCardButton("Packing List", onClick = { navControlador.navigate("PantallaPacking") })
            }
        }
        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                MainCardButton("Usuarios", onClick = { navControlador.navigate("PantallaUsuarios") })
            }
        }
    }
}

@Composable
fun MainCardButton(title: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .size(140.dp),
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




@Composable
fun ProductCard(product: Producto, navController: NavController, onDelete: (String) -> Unit) {
    var show by rememberSaveable { mutableStateOf(false) }
    val imageBitmap = remember(product.imagen) {
        try {
            val bytes = Base64.decode(product.imagen, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        } catch (e: Exception) { null }
    }?.asImageBitmap()

    Card(
        Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(MaterialTheme.colorScheme.primary.copy(alpha=0.2f)),
                contentAlignment = Alignment.Center
            ) {
                imageBitmap?.let {
                    Image(it, contentDescription=product.name, Modifier.fillMaxSize(), contentScale=ContentScale.Crop)
                } ?: Icon(Icons.Filled.Image, contentDescription=null, Modifier.size(48.dp))
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(product.name, maxLines=2, overflow=TextOverflow.Ellipsis)
                Text(
                    text = if (product.description.isEmpty()) "Sin descripción" else product.description,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text("$${product.price}")
            }
            IconButton(onClick={ navController.navigate("ProductoMto?id=${product.id}") }) { Icon(Icons.Filled.Edit, contentDescription="Editar") }
            IconButton(onClick={ show=true }) { Icon(Icons.Filled.Delete, contentDescription="Eliminar") }
        }
        ButtonDelete(show, onDismiss={show=false}, onConfirm={ onDelete(product.id); show=false })
    }
}

@Composable
fun ButtonDelete(show:Boolean, onDismiss:() -> Unit, onConfirm:()-> Unit){
    if (show)
        AlertDialog(
            onDismissRequest = {onDismiss()},
            confirmButton = { TextButton(onClick = {onConfirm()}){Text("Continuar")} },
            dismissButton = { TextButton(onClick = {onDismiss()}){Text("Descartar")} },
            title = { Text("Eliminar producto?") },
            text = { Text("Esta acción no se puede deshacer") }
        )
}

@Preview(showBackground = true)
@Composable
fun PreviewProductCard() {
    MtoProductoTheme {
        ProductCard(Producto("","Ejemplo","0.00","Desc",""), rememberNavController()) {}
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewUIPrincipal() {
    MtoProductoTheme {
        UIPrincipal(rememberNavController())
    }
}
