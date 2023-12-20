package ru.rustore.defold.core

import android.app.Activity

interface IPlayerProvider {
    fun getCurrentActivity(): Activity
}
