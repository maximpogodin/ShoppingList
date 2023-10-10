package com.example.shoppinglist.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Checkbox
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.shoppinglist.models.data.Item
import com.example.shoppinglist.navigation.SHOPPING_LIST_KEY
import com.example.shoppinglist.navigation.Screens
import com.example.shoppinglist.repositories.MainRepository
import com.example.shoppinglist.ui.ShowCircularProgressIndicator
import com.example.shoppinglist.ui.theme.BackgroundColor
import com.example.shoppinglist.viewmodel.ShoppingListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListScreen(navController: NavController) {
    val navBackStackEntry = navController.currentBackStackEntry
    val listId = navBackStackEntry?.arguments?.getInt(SHOPPING_LIST_KEY)!!
    val viewModel = remember { ShoppingListViewModel(listId) }
    val itemsList by viewModel.itemsList.observeAsState(emptyList())

    LaunchedEffect(Unit, block = {
        viewModel.getShoppingList()
    })

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screens.AddToShoppingList.passListId(listId)) }) {
                Icon(Icons.Filled.AddCircle, "")
            }
        },
        content = { padding ->
            if (itemsList.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxHeight(),
                ) {
                    itemsIndexed(items = itemsList, key = { _, i ->
                        i.id
                    }) { _, item ->
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
                                        .align(Alignment.CenterVertically)
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
                                ItemCheckBox(item)
                            },
                            directions = setOf(DismissDirection.EndToStart)
                        )
                    }
                }
            } else {
                //ShowCircularProgressIndicator()
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemCheckBox(item: Item) {
    var checkedState by remember { mutableStateOf(item.is_crossed) }
    var textDecoration by remember { mutableStateOf(setTextDecoration(checkedState)) }

    Card (
        modifier = Modifier
            .clickable { }
            .padding(0.dp, 2.dp),
        shape = RoundedCornerShape(5.dp),
        onClick = {
            MainRepository.crossItOff(item.id)
            checkedState = !checkedState
            textDecoration = setTextDecoration(checkedState)
        }
    ) {
        Row (modifier = Modifier
            .fillMaxSize()
            .height(80.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Checkbox (
                modifier = Modifier
                    .padding(0.dp, 2.dp),
                checked = checkedState,
                onCheckedChange = {
                    MainRepository.crossItOff(item.id)
                    checkedState = !checkedState
                    textDecoration = setTextDecoration(checkedState)
                }
            )
            Text(
                text = "${item.name} - need: ${item.created}",
                fontSize = 20.sp,
                modifier = Modifier.padding(15.dp),
                style = TextStyle(textDecoration = textDecoration)
            )
        }
    }
}

fun setTextDecoration(value: Boolean): TextDecoration {
    return if (value) TextDecoration.LineThrough else TextDecoration.None
}