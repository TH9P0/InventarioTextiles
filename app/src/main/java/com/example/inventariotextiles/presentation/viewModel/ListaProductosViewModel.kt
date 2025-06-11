package com.example.inventariotextiles.presentation.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventariotextiles.data.local.ProductoLocalDataSource
import com.example.inventariotextiles.domain.model.Producto
import com.example.inventariotextiles.presentation.view.states.ListaProductosUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.collections.filter

class ListaProductosViewModel(context:Context): ViewModel() {
    private val dataSource = ProductoLocalDataSource(context)

    private var _productList = MutableStateFlow<List<Producto>>(emptyList())
    val productList: StateFlow<List<Producto>> = _productList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _uiState = MutableStateFlow<ListaProductosUIState>(ListaProductosUIState.Loading)
    val uiState: StateFlow<ListaProductosUIState> = _uiState.asStateFlow()

    init {
        loadProductos()
    }

    fun loadProductos(){
        _isLoading.value = true
        _uiState.value = ListaProductosUIState.Loading
        viewModelScope.launch {
            try {
                val productos = dataSource.getAllProducts()
                _productList.value = productos
                _uiState.value = ListaProductosUIState.Success(productos)
            } catch (e: Exception){
                val errorMsg = "Error al cargar los productos: ${e.message}"
                _errorMessage.value = errorMsg
                _uiState.value = ListaProductosUIState.Error(errorMsg)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteProduct(id: String){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val success = dataSource.deleteProduct(id)
                if(success){
                    val currentList = _productList.value.filter { it.id != id }
                    _productList.value = currentList
                    _uiState.value = ListaProductosUIState.Success(currentList)
                } else{
                    val errorMsg = "No se pudo eliminar el producto"
                    _errorMessage.value = errorMsg
                    _uiState.value = ListaProductosUIState.Error(errorMsg)
                }
            } catch (e: Exception){
                val errorMsg = "Error al eliminar el producto: ${e.message}"
                _errorMessage.value = errorMsg
                _uiState.value = ListaProductosUIState.Error(errorMsg)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearErrorMessage(){
        _errorMessage.value = null
    }
}