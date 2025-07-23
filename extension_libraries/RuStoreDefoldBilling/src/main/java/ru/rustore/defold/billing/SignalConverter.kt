package ru.rustore.defold.billing

import ru.rustore.defold.core.internal.JsonBuilder

object SignalConverter {

    fun paymentLogger(e: Throwable?, message: String): String {
        val throwable = JsonBuilder.toJson(e)

        return "{\"e\": \"$throwable\", \"message\": \"${message}\"}"
    }
}
