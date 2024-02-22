package ru.rustore.defold.billing

import com.google.gson.Gson

object SignalConverter {

    fun paymentLogger(e: Throwable?, message: String): String {
        val gson = Gson()
        val throwable = gson.toJson(e)

        return "{\"e\": \"${throwable}\", \"message\": \"${message}\"}"
    }

    fun purchaseError(purchaseId: String, throwable: Throwable): String {
        return "{\"purchaseId\": \"${purchaseId}\", \"detailMessage\": \"${throwable.message}\"}"
    }
}
