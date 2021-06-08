package com.example.weatha.model.network

import android.util.Log
import com.example.weatha.util.ApiException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

abstract class SafeApiRequest {

    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {
        val response = call.invoke()
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            val error = response.errorBody()
            val message = StringBuilder()
            error?.let {
                try {
                    message.append(JSONObject(it.toString()).getString("message"))
                } catch (e: JSONException) {
                    Log.e("JSONException", e.message.toString())
                }
                message.append("\n")
            }
            message.append("Error Code: ${response.code()}")
            throw ApiException(message.toString())
        }
    }

}