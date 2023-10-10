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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.shoppinglist.navigation.Screens
import com.example.shoppinglist.repositories.MainRepository
import com.example.shoppinglist.util.SharedPreferencesManager
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController) {
    var text by remember { mutableStateOf("") }
    var labelText by remember { mutableStateOf("Enter your key") }
    var labelTextColor by remember { mutableStateOf(Color.Black) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        Modifier.fillMaxSize(),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        TextField(
            value = text,
            onValueChange = {
                if (it.length <= 6) {
                    text = it.trimStart()
                }
            },
            label = { Text(text = labelText, color = labelTextColor) },
            placeholder = { Text("Enter your key") },
            keyboardOptions = KeyboardOptions(KeyboardCapitalization.Characters),
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            if (text.length < 6) {
                labelText = "The key must contain at least 6 characters"
                labelTextColor = Color.Red
            }
            else {
                SharedPreferencesManager.saveApiKey(text)

                coroutineScope.launch {
                    if (!MainRepository.authenticate(text)) {
                        labelText = "Key not found"
                        labelTextColor = Color.Red
                    }
                    else {
                        SharedPreferencesManager.saveApiKey(text)
                        navController.navigate(Screens.ShoppingLists.route)
                    }
                }
            }
        }) {
            Text(text = "Find the key")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            navController.navigate(Screens.GenerateKey.route)
        }) {
            Text(text = "Generate key")
        }
    }
}