package com.example.shoppinglist.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.shoppinglist.models.data.ShoppingList
import com.example.shoppinglist.navigation.Screens
import com.example.shoppinglist.ui.theme.BackgroundColor
import com.example.shoppinglist.viewmodel.ShopListsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListsScreen(navController: NavController) {
    val viewModel: ShopListsViewModel = viewModel()
    val shopLists by viewModel.shoppingLists.observeAsState(emptyList())

    LaunchedEffect(Unit, block = {
        viewModel.getAllMyShopLists()
    })

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screens.CreateShoppingList.route) }) {
                Icon(Icons.Filled.AddCircle, "")
            }
        },
        content = { padding ->
            if (shopLists.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxHeight(),
                ) {
                    itemsIndexed(items = shopLists, key = { _, listItem ->
                        listItem.id
                    }) { index, item ->
                        val currentItem by rememberUpdatedState(item)
                        val state = rememberDismissState(
                            confirmValueChange = {
                                if (it == DismissValue.DismissedToStart) {
                                    viewModel.removeItem(currentItem)
                                }
                                true
                            }
                        )
                        SwipeToDismiss(
                            state = state, background = {
                                val dismissColor = when (state.dismissDirection) {
                                    DismissDirection.StartToEnd -> Color.Transparent
                                    DismissDirection.EndToStart -> Color.Red
                                    null -> BackgroundColor
                                }
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(color = dismissColor)
                                        .height(80.dp)
                                        .padding(20.dp)
                                        .align(CenterVertically)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete",
                                        tint = Color.White,
                                        modifier = Modifier.align(Alignment.CenterEnd)
                                    )
                                }
                            },
                            dismissContent = {
                                ShopListCard(item, navController)
                            },
                            directions = setOf(DismissDirection.EndToStart)
                        )
                    }
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    //ShowCircularProgressIndicator()
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopListCard(shoppingList: ShoppingList, navController: NavController) {
    Card (
        modifier = Modifier
            .clickable { }
            .padding(0.dp, 2.dp),
        shape = RoundedCornerShape(5.dp),
        onClick = {
            navController.navigate(Screens.ShoppingList.passListId(shoppingList.id))
        }
    ) {
        Column (modifier = Modifier
            .fillMaxSize()
            .height(80.dp)) {
            Text(
                text = shoppingList.name,
                fontSize = 20.sp,
                modifier = Modifier.padding(15.dp)
            )
        }
    }
}