package com.arbalestx.barcodescanner;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by arbalestx on 17/05/2016.
 */
public class PrefActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
