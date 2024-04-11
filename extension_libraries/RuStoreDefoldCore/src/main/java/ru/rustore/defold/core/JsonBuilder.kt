package ru.rustore.defold.core

import com.google.gson.Gson

data class SimpleError(
    val simpleName: String?,
    val detailMessage: String?
)

object JsonBuilder {
    private val gson: Gson by lazy { Gson() }

    fun toJson(throwable: Throwable?): String {
        val error = SimpleError(
            simpleName = throwable?.let { it::class.java.simpleName },
            detailMessage = throwable?.message
        )

        return gson.toJson(error)
    }
}
