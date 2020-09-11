package com.nolari.benzinverbrauchberechner.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.nolari.benzinverbrauchberechner.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Nolari on 02.03.2018.
 */

public class NewEntryFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_new_entry, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Calendar cal = Calendar.getInstance();
        String dateFormat = "dd.MM.yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.GERMAN);
        ((EditText)getActivity().findViewById(R.id.input_datefield)).setText(sdf.format(cal.getTime()));

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        boolean isSafeModeOn = preferences.getBoolean(getString(R.string.settings_key_safeMode), true);
        if(!isSafeModeOn){
            getActivity().findViewById(R.id.button_delete).setVisibility(View.VISIBLE);
        }else{
            getActivity().findViewById(R.id.button_delete).setVisibility(View.GONE);
        }
    }
}
