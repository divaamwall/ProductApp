package com.diva.productapp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.diva.productapp.R
import com.diva.productapp.domain.model.Product
import com.diva.productapp.presentation.components.CheckoutPopupComponent
import com.diva.productapp.presentation.components.ErrorComponent
import com.diva.productapp.presentation.components.FooterComponent
import com.diva.productapp.presentation.components.LoaderComponent
import com.diva.productapp.presentation.components.ProductItemComponent
import com.diva.productapp.presentation.components.SortMenuComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    viewModel: ProductViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val products: LazyPagingItems<Product> = uiState.products.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)),
                expandedHeight = 96.dp,
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_phone),
                            contentDescription = stringResource(R.string.app_name),
                            modifier = Modifier.size(48.dp),
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                        Column {
                            Text(
                                text = "Product List",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            Text(
                                text = "${uiState.totalProductCount} products",
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 14.sp,
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Red,
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            FooterComponent(
                totalAmount = uiState.totalAmount,
                hasItems = uiState.hasItems,
                onCheckout = viewModel::onCheckout,
                onReset = viewModel::onResetCart
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ) {
            SortMenuComponent(
                currentSortOption = uiState.currentSortOption,
                expanded = uiState.isSortMenuExpanded,
                onToggleMenu = viewModel::onToggleSortMenu,
                onSortOptionSelected = viewModel::onSortOptionSelected
            )

            HorizontalDivider(
                color = Color.LightGray,
                thickness = 1.dp,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            )

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(products.itemCount) { index ->
                    products[index]?.let { product ->
                        ProductItemComponent(
                            product = product,
                            quantity = uiState.getQuantity(product.id),
                            onIncreaseQuantity = {
                                viewModel.onIncreaseQuantity(product.id, product.stock)
                            },
                            onDecreaseQuantity = {
                                viewModel.onDecreaseQuantity(product.id)
                            }
                        )
                    }
                }

                when {
                    products.loadState.refresh is LoadState.Loading -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillParentMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                LoaderComponent()
                            }
                        }
                    }

                    products.loadState.append is LoadState.Loading -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(32.dp),
                                    color = Color.Red
                                )
                            }
                        }
                    }

                    products.loadState.refresh is LoadState.Error -> {
                        item {
                            Box(
                                modifier = Modifier.fillParentMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                ErrorComponent(
                                    message = (products.loadState.refresh as LoadState.Error).error.localizedMessage
                                        ?: "An error occurred"
                                )
                            }
                        }
                    }

                    products.loadState.append is LoadState.Error -> {
                        item {
                            ErrorComponent(
                                message = (products.loadState.append as LoadState.Error).error.localizedMessage
                                    ?: "An error occurred while loading more items"
                            )
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

        CheckoutPopupComponent(
            showDialog = uiState.showCheckoutDialog,
            purchasedItems = viewModel.getPurchasedItems(),
            totalAmount = uiState.totalAmount,
            onClose = viewModel::onCloseCheckoutDialog
        )

    }
}