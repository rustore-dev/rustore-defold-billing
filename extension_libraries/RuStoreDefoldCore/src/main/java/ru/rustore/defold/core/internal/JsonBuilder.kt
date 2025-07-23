package ru.rustore.defold.core.internal

import com.google.gson.Gson
import ru.rustore.defold.core.model.SimpleError

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
