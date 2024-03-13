package ru.rustore.defold.core.wrappers;

import android.app.Activity;
import ru.rustore.defold.core.IPlayerProvider;

public class DefoldPlayerWrapper implements IPlayerProvider {

    private native Activity NativeOnActivityRequest();

    public Activity getCurrentActivity() {
        return NativeOnActivityRequest();
    }
}
