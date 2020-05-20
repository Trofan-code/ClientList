package com.example.clientlist.settings;

import android.os.Bundle;

import com.example.clientlist.R;

import androidx.preference.PreferenceFragmentCompat;

public class MySettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.main_settings,rootKey);
    }
}
