package com.example.shoppinglist.util
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

class KeystoreManager {
    private companion object {
        const val KEY_NAME = "MY_KEY"
        const val IV_SEPARATOR = ";"
    }

    private val keyStore: KeyStore = KeyStore.getInstance("AndroidKeyStore")

    init {
        keyStore.load(null)
        if (!keyStore.containsAlias(KEY_NAME)) {
            generateSecretKey()
        }
    }

    fun encryptData(data: String): String {
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        val secretKey = getSecretKey()
        var result = ""

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, cipher.parameters)
        val iv = cipher.iv
        val ivString = Base64.encodeToString(iv, Base64.DEFAULT)
        result = ivString + IV_SEPARATOR

        val bytes = cipher.doFinal(data.toByteArray())
        result += Base64.encodeToString(bytes, Base64.DEFAULT)

        return result
    }

    fun decryptData(data: String): String {
        var encodedString: String

        val split = data.split(IV_SEPARATOR.toRegex())
        if (split.size != 2) throw IllegalArgumentException("Passed data is incorrect. There was no IV specified with it.")

        val ivBytes = Base64.decode(split[0], Base64.DEFAULT)
        encodedString = split[1]
        val spec = GCMParameterSpec(128, ivBytes)
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        val secretKey = getSecretKey()
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)

        val encryptedData = Base64.decode(encodedString, Base64.DEFAULT)
        val decodedData = cipher.doFinal(encryptedData)
        return String(decodedData)
    }

    private fun generateSecretKey() {
        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
        val keyGenParameterSpec = KeyGenParameterSpec.Builder(
            KEY_NAME,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .build()

        keyGenerator.init(keyGenParameterSpec)
        keyGenerator.generateKey()
    }

    private fun getSecretKey(): SecretKey {
        return (keyStore.getKey(KEY_NAME, null)) as SecretKey
    }
}