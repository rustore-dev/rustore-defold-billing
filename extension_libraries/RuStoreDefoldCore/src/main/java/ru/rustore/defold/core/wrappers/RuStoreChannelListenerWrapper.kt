package ru.rustore.defold.core.wrappers

import ru.rustore.defold.core.callbacks.IRuStoreChannelListener

class RuStoreChannelListenerWrapper(cppPointer: Long): IRuStoreChannelListener {
    private val mutex = Any()
    private var cppPointer: Long = 0
    private external fun nativeOnMessage(pointer: Long, channel: String, value: String)

    init {
        this.cppPointer = cppPointer
    }

    override fun onMessage(channel: String, value: String) {
        synchronized(mutex) {
            if (cppPointer != 0L) {
                nativeOnMessage(cppPointer, channel, value)
            }
        }
    }

    override fun disposeCppPointer() {
        synchronized(mutex) { cppPointer = 0 }
    }
}
