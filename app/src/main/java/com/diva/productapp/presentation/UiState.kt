package com.diva.productapp.presentation

import androidx.paging.PagingData
import com.diva.productapp.domain.model.Product
import com.diva.productapp.domain.model.SortOption
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class UiState(
    val isLoading: Boolean = false,
    val products: Flow<PagingData<Product>> = emptyFlow(),
    val cartItems: Map<Int, Int> = emptyMap(),
    val error: String? = null,
    val currentSortOption: SortOption = SortOption.DEFAULT,
    val isSortMenuExpanded: Boolean = false,
    val showCheckoutDialog: Boolean = false,
    val totalAmount: Double = 0.0,
    val totalQuantity: Int = 0,
    val totalProductCount: Int = 0
) {
    val hasItems: Boolean
        get() = totalQuantity > 0

    fun getQuantity(productId: Int): Int {
        return cartItems[productId] ?: 0
    }
}