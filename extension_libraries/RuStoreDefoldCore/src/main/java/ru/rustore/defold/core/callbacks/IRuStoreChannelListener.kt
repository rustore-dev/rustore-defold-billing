package ru.rustore.defold.core.callbacks

interface IRuStoreChannelListener {
    fun onMessage(channel: String, value: String)
    fun onMessage(channel: String, value0: String, value1: String)
    fun disposeCppPointer()
}
