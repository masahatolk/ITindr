package com.hits.itindr.network

class ApiException(
    val statusCode: Int,
    override val message: String,
) : Exception(message)