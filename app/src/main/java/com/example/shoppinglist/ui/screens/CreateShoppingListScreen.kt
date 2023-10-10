package com.example.shoppinglist.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.shoppinglist.repositories.MainRepository
import com.example.shoppinglist.util.SharedPreferencesManager

@Composable
fun CreateShoppingListScreen(navController: NavController) {
    var shopListNameText by remember { mutableStateOf("") }
    var shopListNameLabelText by remember { mutableStateOf("Enter shop list name") }
    var shopListLabelTextColor by remember { mutableStateOf(Color.Black) }

    Column(
        Modifier.fillMaxSize(),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        TextField(
            value = shopListNameText,
            onValueChange = { shopListNameText = it },
            label = { Text(text = shopListNameLabelText, color = shopListLabelTextColor) },
            placeholder = { Text("Enter shop list name") },
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            if (shopListNameText.isEmpty()) {
                shopListNameLabelText = "The name must contain at least one character"
                shopListLabelTextColor = Color.Red
            }
            else {
                val key = SharedPreferencesManager.getApiKey()
                if (key == null) {
                    shopListNameLabelText = "An error occurred while creating the list"
                    shopListLabelTextColor = Color.Red
                }
                else {
                    MainRepository.createShoppingList(key, shopListNameText)
                    navController.popBackStack()
                }
            }
        }) {
            Text(text = "Create")
        }
    }
}