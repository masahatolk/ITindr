package com.hits.core_auth

interface TokenStore {
    fun getToken(): String?
    fun saveToken(token: String)
    fun clearToken()
}