package com.example.shoppinglist.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.shoppinglist.navigation.SHOPPING_LIST_KEY
import com.example.shoppinglist.repositories.MainRepository
import com.example.shoppinglist.util.SharedPreferencesManager

@Composable
fun AddToShoppingListScreen(navController: NavController) {
    var itemNameText by remember { mutableStateOf("") }
    var itemNameLabelText by remember { mutableStateOf("Enter item name") }
    var itemNameLabelTextColor by remember { mutableStateOf(Color.Black) }

    var itemValueText by remember { mutableStateOf("") }
    var itemValueLabelText by remember { mutableStateOf("Enter item value") }
    var itemValueLabelTextColor by remember { mutableStateOf(Color.Black) }

    val navBackStackEntry = navController.currentBackStackEntry
    val listId = navBackStackEntry?.arguments?.getInt(SHOPPING_LIST_KEY)!!

    Column(
        Modifier.fillMaxSize(),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        TextField(
            value = itemNameText,
            onValueChange = { itemNameText = it },
            label = { Text(text = itemNameLabelText, color = itemNameLabelTextColor) },
            placeholder = { Text("Enter item name") },
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            value = itemValueText,
            onValueChange = { itemValueText = it },
            label = { Text(text = itemValueLabelText, color = itemValueLabelTextColor) },
            placeholder = { Text("Enter item value") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            val itemValue = if (itemValueText.isEmpty()) 0 else itemValueText.toInt()

            if (itemNameText.isEmpty()) {
                itemNameLabelText = "The name must contain at least one character"
                itemNameLabelTextColor = Color.Red
            }
            else if (itemValue < 1) {
                itemValueLabelText = "The quantity must be greater than 0"
                itemValueLabelTextColor = Color.Red
            }
            else {
                val key = SharedPreferencesManager.getApiKey()
                if (key == null) {
                    itemNameLabelText = "An error occurred while creating the item"
                    itemNameLabelTextColor = Color.Red
                } else {
                    MainRepository.addToShoppingList(listId, itemNameText, itemValueText.toInt())
                    navController.popBackStack()
                }
            }
        }) {
            Text(text = "Create")
        }
    }
}