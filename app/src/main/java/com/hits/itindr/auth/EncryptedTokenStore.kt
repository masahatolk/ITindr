package com.hits.itindr.auth

import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import androidx.core.content.edit

class EncryptedTokenStore(context: Context) : TokenStore {
    private val preferences: SharedPreferences = context.getSharedPreferences(
        PREFERENCES_NAME,
        Context.MODE_PRIVATE,
    )

    override fun getToken(): String? {
        val encryptedToken = preferences.getString(KEY_TOKEN, null) ?: return null
        val separatorIndex = encryptedToken.indexOf(SEPARATOR)
        if (separatorIndex <= 0) return null

        return runCatching {
            val iv = Base64.decode(encryptedToken.substring(0, separatorIndex), Base64.NO_WRAP)
            val payload = Base64.decode(encryptedToken.substring(separatorIndex + 1), Base64.NO_WRAP)
            val cipher = Cipher.getInstance(TRANSFORMATION).apply {
                init(Cipher.DECRYPT_MODE, getOrCreateSecretKey(), GCMParameterSpec(GCM_TAG_LENGTH_BITS, iv))
            }
            cipher.doFinal(payload).decodeToString()
        }.getOrNull()
    }

    override fun saveToken(token: String) {
        val cipher = Cipher.getInstance(TRANSFORMATION).apply {
            init(Cipher.ENCRYPT_MODE, getOrCreateSecretKey())
        }
        val encryptedToken = cipher.doFinal(token.encodeToByteArray())
        val serialized = listOf(cipher.iv, encryptedToken)
            .joinToString(SEPARATOR.toString()) { Base64.encodeToString(it, Base64.NO_WRAP) }

        preferences.edit { putString(KEY_TOKEN, serialized) }
    }

    override fun clearToken() {
        preferences.edit { remove(KEY_TOKEN) }
    }

    private fun getOrCreateSecretKey(): SecretKey {
        val keyStore = KeyStore.getInstance(ANDROID_KEY_STORE).apply { load(null) }
        (keyStore.getEntry(KEY_ALIAS, null) as? KeyStore.SecretKeyEntry)?.let { entry ->
            return entry.secretKey
        }

        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEY_STORE)
        val keySpec = KeyGenParameterSpec.Builder(
            KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT,
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(KEY_SIZE_BITS)
            .build()

        keyGenerator.init(keySpec)
        return keyGenerator.generateKey()
    }

    private companion object {
        const val PREFERENCES_NAME = "secure_auth_storage"
        const val KEY_TOKEN = "access_token"
        const val KEY_ALIAS = "itindr_access_token_key"
        const val ANDROID_KEY_STORE = "AndroidKeyStore"
        const val TRANSFORMATION = "AES/GCM/NoPadding"
        const val GCM_TAG_LENGTH_BITS = 128
        const val KEY_SIZE_BITS = 256
        const val SEPARATOR = ':'
    }
}
