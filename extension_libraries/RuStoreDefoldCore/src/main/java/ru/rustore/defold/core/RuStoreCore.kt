package ru.rustore.defold.core

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.util.Log
import android.widget.Toast
import ru.rustore.defold.core.callbacks.IRuStoreChannelListener

object RuStoreCore {
    private const val CLIP_DATA_TOOLTIP = "Copied Text"

    private var listener: IRuStoreChannelListener? = null

    fun setChannelListener(listener: IRuStoreChannelListener) {
        RuStoreCore.listener = listener
    }

    fun emitSignal(channel: String, value: String) {
        listener?.run {
            onMessage(channel, value)
        }
    }

    fun showToast(activity: Activity, message: String) {
        activity.runOnUiThread { Toast.makeText(activity, message, Toast.LENGTH_LONG).show() }
    }

    fun logVerbose(tag: String, message: String) {
        Log.v(tag, message)
    }

    fun logDebug(tag: String, message: String) {
        Log.d(tag, message)
    }

    fun logInfo(tag: String, message: String) {
        Log.i(tag, message)
    }

    fun logWarning(tag: String, message: String) {
        Log.w(tag, message)
    }

    fun logError(tag: String, message: String) {
        Log.e(tag, message)
    }

    fun copyToClipboard(activity: Activity, text: String) {
        val clipboard: ClipboardManager = activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(CLIP_DATA_TOOLTIP, text)
        clipboard.setPrimaryClip(clip)
    }

    fun getFromClipboard(activity: Activity): String {
        val clipboard: ClipboardManager = activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = clipboard.primaryClip ?: return String()
        val text = clip.getItemAt(0).text ?: return String()
        return text.toString()
    }

    fun getStringResources(activity: Activity, name: String): String {
        val application = activity.application
        val id: Int = application.resources.getIdentifier(name, "string", application.packageName)
        return application.getString(id)
    }
}
