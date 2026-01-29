package com.diva.productapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.diva.productapp.domain.model.CartItem
import com.diva.productapp.domain.model.Product
import com.diva.productapp.domain.model.SortOption
import com.diva.productapp.domain.usecase.GetProductUseCase
import com.diva.productapp.domain.usecase.GetTotalProductCountUseCase
import com.diva.productapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductsUseCase: GetProductUseCase,
    private val getTotalProductCountUseCase: GetTotalProductCountUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    private val allProducts = mutableMapOf<Int, Product>()

    init {
        loadProducts(uiState.value.currentSortOption)
        loadProductsTotalCounts()
    }

    private fun loadProducts(sortOption: SortOption) {
        val productsFlow = getProductsUseCase(sortOption)
            .map { pagingData ->
                pagingData.map { product ->
                    allProducts[product.id] = product
                    product
                }
            }
            .cachedIn(viewModelScope)

        _uiState.update {
            it.copy(
                currentSortOption = sortOption,
                products = productsFlow,
                isSortMenuExpanded = false
            )
        }
    }

    private fun loadProductsTotalCounts() {
        viewModelScope.launch {
            getTotalProductCountUseCase().collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                totalProductCount = resource.data ?: 0,
                                isLoading = false
                            )
                        }
                    }

                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                error = resource.message,
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    fun onIncreaseQuantity(productId: Int, currentStock: Int) {
        _uiState.update { state ->
            val currentQuantity = state.cartItems[productId] ?: 0

            if (currentQuantity < currentStock) {
                val updatedCartItems = state.cartItems.toMutableMap().apply {
                    put(productId, currentQuantity + 1)
                }

                state.copy(
                    cartItems = updatedCartItems,
                    totalAmount = calculateTotal(updatedCartItems),
                    totalQuantity = calculateTotalQuantity(updatedCartItems),
                    isSortMenuExpanded = false
                )
            } else {
                state
            }
        }
    }

    fun onDecreaseQuantity(productId: Int) {
        _uiState.update { state ->
            val currentQuantity = state.cartItems[productId] ?: 0

            if (currentQuantity > 0) {
                val updatedCartItems = state.cartItems.toMutableMap().apply {
                    val newQuantity = currentQuantity - 1
                    if (newQuantity == 0) {
                        remove(productId)
                    } else {
                        put(productId, newQuantity)
                    }
                }

                state.copy(
                    cartItems = updatedCartItems,
                    totalAmount = calculateTotal(updatedCartItems),
                    totalQuantity = calculateTotalQuantity(updatedCartItems),
                    isSortMenuExpanded = false
                )
            } else {
                state
            }
        }
    }


    fun onSortOptionSelected(sortOption: SortOption) {
        loadProducts(sortOption)
    }

    fun onToggleSortMenu() {
        _uiState.update { it.copy(isSortMenuExpanded = !it.isSortMenuExpanded) }
    }

    fun onCloseSortMenu() {
        _uiState.update { it.copy(isSortMenuExpanded = false) }
    }

    fun onCheckout() {
        if (_uiState.value.hasItems) {
            _uiState.update { it.copy(showCheckoutDialog = true, isSortMenuExpanded = false) }
        }
    }

    fun onCloseCheckoutDialog() {
        resetCart()
    }

    fun onResetCart() {
        resetCart()
    }

    private fun resetCart() {
        _uiState.update {
            it.copy(
                cartItems = emptyMap(),
                totalAmount = 0.0,
                totalQuantity = 0,
                showCheckoutDialog = false,
                isSortMenuExpanded = false
            )
        }
    }

    private fun calculateTotal(cartItems: Map<Int, Int>): Double {
        return cartItems.entries.sumOf { (productId, quantity) ->
            val product = allProducts[productId]
            (product?.price ?: 0.0) * quantity
        }
    }

    private fun calculateTotalQuantity(cartItems: Map<Int, Int>): Int {
        return cartItems.values.sum()
    }

    fun getPurchasedItems(): List<CartItem> {
        return _uiState.value.cartItems.mapNotNull { (productId, quantity) ->
            allProducts[productId]?.let { product ->
                CartItem(product = product, quantity = quantity)
            }
        }
    }

}