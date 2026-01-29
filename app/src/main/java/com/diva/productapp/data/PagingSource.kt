package com.diva.productapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.diva.productapp.data.remote.network.ApiService
import com.diva.productapp.domain.model.Product
import com.diva.productapp.domain.model.SortOption
import com.diva.productapp.utils.DataMapper.toProducts
import retrofit2.HttpException
import java.io.IOException

class PagingSource(
    private val apiService: ApiService,
    private val sortOption: SortOption
) : PagingSource<Int, Product>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        return try {
            val position = params.key ?: 0
            val response = apiService.getList(
                limit = params.loadSize,
                skip = position
            )

            var products = response.products.toProducts()

            products = when (sortOption) {
                SortOption.DEFAULT -> products
                SortOption.LOWEST_PRICE -> products.sortedBy { it.price }
                SortOption.HIGHEST_PRICE -> products.sortedByDescending { it.price }
                SortOption.NAME -> products.sortedBy { it.title.lowercase() }
            }

            val total = response.total ?: 0

            LoadResult.Page(
                data = products,
                prevKey = if (position == 0) null else position - params.loadSize,
                nextKey = if (position + params.loadSize >= total) null else position + params.loadSize
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(state.config.pageSize)
                ?: anchorPage?.nextKey?.minus(state.config.pageSize)
        }
    }
}