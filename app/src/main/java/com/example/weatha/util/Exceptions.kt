package com.example.weatha.util

import java.io.IOException

/**
 * Handing exceptions
 */
class ApiException(message: String) : IOException(message)
class NoInternetException(message: String) : IOException(message)