package com.example.shoppinglist

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.shoppinglist.navigation.NavigationGraph
import com.example.shoppinglist.ui.theme.ShoppingListTheme
import com.example.shoppinglist.util.SharedPreferencesManager

class MainActivity : ComponentActivity() {
    companion object {
        private var instance: MainActivity? = null

        fun getInstance(): MainActivity? {
            return instance
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this

        SharedPreferencesManager.initialize(this)
        setContent {
            ShoppingListTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavigationGraph(navController = navController)
                }
            }
        }
    }

    fun copyTextToClipBoard(text: String) {
        val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.setPrimaryClip(ClipData.newPlainText("Key", text))
    }

    fun pasteTextFromClipBoard() : String? {
        val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager

        if (!clipboard.hasPrimaryClip())
            return null

        return clipboard.primaryClip!!.getItemAt(0).text.toString()
    }
}