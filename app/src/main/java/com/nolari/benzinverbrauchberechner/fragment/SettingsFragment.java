package com.nolari.benzinverbrauchberechner.fragment;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.nolari.benzinverbrauchberechner.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}