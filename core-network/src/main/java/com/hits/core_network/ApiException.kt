package com.hits.core_network

class ApiException(
    val statusCode: Int,
    override val message: String,
) : Exception(message)