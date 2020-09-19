package com.nolari.benzinverbrauchberechner.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nolari.benzinverbrauchberechner.activity.DetailViewActivity;
import com.nolari.benzinverbrauchberechner.R;
import com.nolari.benzinverbrauchberechner.adapter.TankListAdapter;
import com.nolari.benzinverbrauchberechner.database.DatabaseSingleton;
import com.nolari.benzinverbrauchberechner.database.TankDatabase;
import com.nolari.benzinverbrauchberechner.database.TankEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nolari on 02.03.2018.
 */

public class GraphFragment extends Fragment implements AdapterView.OnItemClickListener{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_graph, null);

        TankDatabase database = DatabaseSingleton.getInstance(getActivity().getApplicationContext());
        List<TankEntry> tankEntries = database.getTankEntryDao().getNewestEntries(20);
        ArrayList<TankEntry> tankEntriesArray = new ArrayList<>(tankEntries);

        TankListAdapter adapter = new TankListAdapter(getActivity().getApplicationContext(), R.layout.list_view_tankentry, tankEntriesArray);
        ListView listView = v.findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TankEntry tankEntry = (TankEntry) parent.getAdapter().getItem(position);

        Intent intent = new Intent(getContext(), DetailViewActivity.class);
        intent.putExtra("TANKENTRY_UID", tankEntry.getUid());
        startActivity(intent);
    }
}
