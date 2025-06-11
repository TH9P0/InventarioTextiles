package com.example.inventariotextiles.presentation.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.let
import kotlin.text.isBlank
import kotlin.text.matches
import kotlin.text.toDoubleOrNull

class MtoProductoViewModel: ViewModel() {

    private val _nombre = MutableStateFlow<String>("")
    val nombre: StateFlow<String> = _nombre

    private val _precio = MutableStateFlow<String>("")
    val precio: StateFlow<String> = _precio

    private val _descripcion = MutableStateFlow<String>("")
    val descripcion: StateFlow<String> = _descripcion

    private val _botonEnabled = MutableStateFlow<Boolean>(false)
    val botonEnabled: StateFlow<Boolean> = _botonEnabled

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun onProductoChanged(nombre: String, precio: String, descripcion: String){
        _nombre.value = nombre
        _precio.value = precio
        _descripcion.value = descripcion

        _botonEnabled.value = isValidNombre(nombre) && isValidPrecio(precio) && isValidDescripcion(descripcion)
    }
    private fun isValidNombre(nombre: String): Boolean {
        if (nombre.isBlank()) return false

        val regexNombre = Regex("^[\\p{L}0-9 .,;:!¡¿?()-]+$")
        return nombre.matches(regexNombre)
    }

    private fun isValidDescripcion(descripcion: String): Boolean {
        if (descripcion.isBlank()) return false

        val regexDescripcion = Regex("^[\\p{L}0-9 .,;:!¡¿?()-]+$")
        return descripcion.matches(regexDescripcion)
    }

    private fun isValidPrecio(precio: String): Boolean {
        if (precio.isBlank()) return false

        val regexPrecio = Regex("^\\d+\\.?\\d*$")

        return precio.matches(regexPrecio) && precio.toDoubleOrNull()?.let { it >= 0 } == true
    }

    suspend fun onProductSelected(){
        _isLoading.value = true
        delay(4000)
        _isLoading.value = false
    }
}