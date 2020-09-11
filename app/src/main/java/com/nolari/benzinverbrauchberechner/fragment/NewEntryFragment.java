package com.nolari.benzinverbrauchberechner.fragment;

import android.app.Fragment;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nolari.benzinverbrauchberechner.R;

/**
 * Created by Nolari on 02.03.2018.
 */

public class NewEntryFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_new_entry, null);
    }

}
