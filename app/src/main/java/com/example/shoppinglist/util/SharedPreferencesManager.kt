package com.example.shoppinglist.util

import android.content.Context

class SharedPreferencesManager (context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val keystoreManager = KeystoreManager()

    fun saveApiKey(apiKey: String) {
        val encryptedData = keystoreManager.encryptData(apiKey)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_ALIAS, encryptedData)
        editor.apply()
    }

    fun getApiKey(): String? {
        val apiKeyBase64 = sharedPreferences.getString(KEY_ALIAS, null)

        return if (apiKeyBase64 != null) {
            keystoreManager.decryptData(apiKeyBase64)
        } else {
            null
        }
    }

    companion object {
        const val PREFS_NAME = "SECURE_PREFS"
        const val KEY_ALIAS = "ENCRYPTED_API_KEY"

        private var instance: SharedPreferencesManager? = null

        fun getInstance(): SharedPreferencesManager {
            return instance!!
        }

        fun initialize(context: Context) {
            instance = SharedPreferencesManager(context)
        }

        fun saveApiKey(apiKey: String) {
            instance!!.saveApiKey(apiKey)
        }

        fun getApiKey(): String? {
            return instance!!.getApiKey()
        }
    }
}