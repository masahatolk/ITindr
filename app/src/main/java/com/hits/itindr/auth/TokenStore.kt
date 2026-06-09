package com.hits.itindr.auth

interface TokenStore {
    fun getToken(): String?
    fun saveToken(token: String)
    fun clearToken()
}