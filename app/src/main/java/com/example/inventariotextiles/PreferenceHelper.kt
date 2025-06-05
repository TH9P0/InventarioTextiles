package com.example.inventariotextiles

import android.content.Context
import androidx.core.content.edit

class PreferenceHelper(context: Context) {
    private val prefs = context.getSharedPreferences("mis_preferencias", Context.MODE_PRIVATE)

    fun guardarColorUsuario(colorFavorito: String){
        prefs.edit { putString("color_favorito", colorFavorito) }
    }

    fun leerColorUsuario(): String?{
        return prefs.getString("color_favorito",null)
    }
}