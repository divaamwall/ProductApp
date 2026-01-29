package com.diva.productapp.domain.usecase

import androidx.paging.PagingData
import com.diva.productapp.domain.model.Product
import com.diva.productapp.domain.model.SortOption
import com.diva.productapp.domain.repository.ProductRepository
import com.diva.productapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke(sortOption: SortOption): Flow<PagingData<Product>>{
        return repository.getProductsPaged(sortOption)
    }
}

class GetTotalProductCountUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(): Flow<Resource<Int>> {
        return repository.getTotalProductCount()
    }
}