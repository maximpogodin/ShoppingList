package com.example.shoppinglist.ui.screens

import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.NavController
import com.example.shoppinglist.MainActivity
import com.example.shoppinglist.repositories.MainRepository
import com.example.shoppinglist.util.SharedPreferencesManager

@Composable
fun GenerateKeyScreen(navController: NavController) {
    LaunchedEffect(Unit, block = {
        MainRepository.createApiKey()
    })

    CreateContent(navController)
}

@Composable
fun CreateContent(navController: NavController) {
    Column(
        Modifier.fillMaxSize(),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        val key = SharedPreferencesManager.getInstance().getApiKey()
        if (key != null) {
            Text(key)
            Button(onClick = {
                navController.popBackStack()
                MainActivity.getInstance()!!.copyTextToClipBoard(key)
            }) {
                Text(text = "Copy")
            }
        }
        else {
            Text("The key could not be generated")
        }
    }
}