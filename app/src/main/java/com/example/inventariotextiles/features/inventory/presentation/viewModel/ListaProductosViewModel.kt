package com.example.inventariotextiles.features.inventory.presentation.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.inventariotextiles.data.local.PrendaLocalDataSourceImpl
import com.example.inventariotextiles.domain.model.Prenda
import com.example.inventariotextiles.presentation.view.states.ListaProductosUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListaProductosViewModel(application: Application) : AndroidViewModel(application) {
    private val dataSource = PrendaLocalDataSourceImpl(application.applicationContext)

    private val _prendaList = MutableStateFlow<List<Prenda>>(emptyList())
    val prendaList: StateFlow<List<Prenda>> = _prendaList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _uiState = MutableStateFlow<ListaProductosUIState>(ListaProductosUIState.Loading)
    val uiState: StateFlow<ListaProductosUIState> = _uiState.asStateFlow()

    init {
        loadProductos()
    }

    fun loadProductos() {
        viewModelScope.launch {
            _isLoading.value = true
            _uiState.value = ListaProductosUIState.Loading
            try {
                val productos = withContext(Dispatchers.IO) {
                    dataSource.getAllPrendas()
                }
                _prendaList.value = productos
                _uiState.value = ListaProductosUIState.Success(productos)
            } catch (e: Exception) {
                val errorMsg = "Error al cargar productos: ${e.message}"
                _errorMessage.value = errorMsg
                _uiState.value = ListaProductosUIState.Error(errorMsg)
                Log.e("ListaProductosVM", errorMsg, e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deletePrenda(id: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val success = withContext(Dispatchers.IO) {
                    dataSource.deletePrenda(id)
                }
                if (success) {
                    val currentList = _prendaList.value.filter { it.id != id }
                    _prendaList.value = currentList
                    _uiState.value = ListaProductosUIState.Success(currentList)
                } else {
                    val errorMsg = "No se pudo eliminar la prenda"
                    _errorMessage.value = errorMsg
                    _uiState.value = ListaProductosUIState.Error(errorMsg)
                }
            } catch (e: Exception) {
                val errorMsg = "Error al eliminar la prenda: ${e.message}"
                _errorMessage.value = errorMsg
                _uiState.value = ListaProductosUIState.Error(errorMsg)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }

    // Factory para la inyección de dependencias
    class Factory(private val application: Application) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ListaProductosViewModel(application) as T
        }
    }
}