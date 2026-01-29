package com.diva.productapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.diva.productapp.data.PagingSource
import com.diva.productapp.data.remote.network.ApiService
import com.diva.productapp.domain.model.Product
import com.diva.productapp.domain.model.SortOption
import com.diva.productapp.domain.repository.ProductRepository
import com.diva.productapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ProductRepository {
    override fun getProductsPaged(sortOption: SortOption): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                initialLoadSize = 20
            ),
            pagingSourceFactory = { PagingSource(apiService, sortOption) }
        ).flow
    }

    override suspend fun getTotalProductCount(): Flow<Resource<Int>> = flow {
        emit(Resource.Loading())

        try {
            val response = apiService.getList(limit = 1, skip = 0)
            val total = response.total ?: 0
            emit(Resource.Success(total))
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Oops, something went wrong: ${e.localizedMessage}"
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Couldn't reach server, check your internet connection"
                )
            )
        } catch (e: Exception) {
            emit(
                Resource.Error(
                    message = "An unexpected error occurred: ${e.localizedMessage}"
                )
            )
        }
    }
}