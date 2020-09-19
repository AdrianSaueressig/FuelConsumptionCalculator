package com.nolari.benzinverbrauchberechner.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nolari.benzinverbrauchberechner.R;
import com.nolari.benzinverbrauchberechner.adapter.TankListAdapter;
import com.nolari.benzinverbrauchberechner.database.DatabaseSingleton;
import com.nolari.benzinverbrauchberechner.database.TankDatabase;
import com.nolari.benzinverbrauchberechner.database.TankEntry;

import java.util.ArrayList;
import java.util.List;

public class DetailViewFragment extends Fragment{

    int uid = -1;

    public DetailViewFragment(int uid){
        this.uid = uid;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detailview, container, false);

        TankEntry tankEntry = DatabaseSingleton.getInstance(getContext()).getTankEntryDao().getEntry(uid);
        // TODO fill detail view fields with data
        TextView tv = v.findViewById(R.id.text3);
        tv.setText(tankEntry.toString());

        return v;
    }

}
