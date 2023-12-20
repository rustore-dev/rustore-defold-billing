package ru.rustore.defold.core.callbacks

interface IRuStoreChannelListener {
    fun onMessage(channel: String, value: String)
    fun disposeCppPointer()
}
