package com.diva.productapp.domain.repository

import androidx.paging.PagingData
import com.diva.productapp.domain.model.Product
import com.diva.productapp.domain.model.SortOption
import com.diva.productapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProductsPaged(sortOption: SortOption): Flow<PagingData<Product>>
    suspend fun getTotalProductCount(): Flow<Resource<Int>>
}