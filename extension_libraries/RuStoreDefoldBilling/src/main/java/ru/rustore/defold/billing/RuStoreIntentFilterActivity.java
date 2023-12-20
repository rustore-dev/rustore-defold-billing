package ru.rustore.defold.billing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class RuStoreIntentFilterActivity extends Activity {

    private static Class<?> gameActivityClass = null;

    public static synchronized void setGameActivityClass(Class<?> value) {
        gameActivityClass = value;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            RuStoreBilling.onNewIntent(getIntent());
            startGameActivity(null);
        }
    }

    @Override
    public void onNewIntent(Intent newIntent)
    {
        super.onNewIntent(newIntent);
        setIntent(newIntent);

        RuStoreBilling.onNewIntent(newIntent);
        startGameActivity(newIntent);
    }

    private void startGameActivity(Intent intent) {
        if (gameActivityClass != null) {
            Intent newIntent = new Intent(this, gameActivityClass);
            if (intent != null) newIntent.putExtras(intent.getExtras());
            startActivity(newIntent);
        }
    }
}
