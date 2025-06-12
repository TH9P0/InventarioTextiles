package com.example.inventariotextiles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.inventariotextiles.Screens.LoginScreen
import com.example.inventariotextiles.Screens.PantallaAuditoria
import com.example.inventariotextiles.Screens.PantallaAyuda
import com.example.inventariotextiles.Screens.PantallaContenedores
import com.example.inventariotextiles.Screens.PantallaPackingList
import com.example.inventariotextiles.Screens.PantallaTransferencia
import com.example.inventariotextiles.Screens.PantallaUsuarios
import com.example.inventariotextiles.Screens.ProductoAlmacen
import com.example.inventariotextiles.Screens.RGBColorPicker
import com.example.inventariotextiles.Screens.UIPrincipal
import com.example.inventariotextiles.ui.theme.MtoProductoTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MtoProductoTheme {
                Navegacion()
            }
        }
    }
}

@Composable
fun Navegacion(){
    val context = LocalContext.current
    val navController = rememberNavController()
    NavHost(navController, startDestination = "PantallaLogin"){
        composable("PantallaLogin") {
            LoginScreen(context, navController)
        }
        composable("ColorPicker") {
            RGBColorPicker(context,navController)
        }
        composable("PantallaAyuda"){
            PantallaAyuda(navController)
        }
        composable("PantallaUsuarios"){
            PantallaUsuarios(navController)
        }

        composable("PantallaAuditoria"){
            PantallaAuditoria(navController)
        }
        composable("PantallaPacking"){
            PantallaPackingList(navController)
        }
        composable("PantallaAlmacen") {  // Nombre corregido
            ProductoAlmacen(id = null, navController = navController)
        }
        composable("PantallaPrincipal") {  // Nombre corregido
            UIPrincipal(navController)
        }
        composable("PantallaTransferir") {
            PantallaTransferencia(navController)
        }
        composable("PantallaContenedores") {
            PantallaContenedores(navController)
        }
        composable(
            "ProductoMto?id={id}",
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            })

        ){ backStackEntry ->
            ProductoAlmacen(
                id = backStackEntry.arguments?.getString("id"),
                navController = navController
            )
        }
    }
}

data class Producto(
    val id: String,
    val name: String,
    val price: String,
    val description: String,
    val imagen: String
)

@Preview(showBackground = true)
@Composable
fun PreviewUIPrincipal() {
    MtoProductoTheme { UIPrincipal(rememberNavController()) }
}