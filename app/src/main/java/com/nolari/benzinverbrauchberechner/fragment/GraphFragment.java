package com.nolari.benzinverbrauchberechner.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nolari.benzinverbrauchberechner.HomeScreen;
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
        ArrayList<TankEntry> tankEntriesArray = new ArrayList<TankEntry>(tankEntries);

        TankListAdapter adapter = new TankListAdapter(getActivity().getApplicationContext(), R.layout.list_view_tankentry, tankEntriesArray);
        ListView listView = v.findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ((HomeScreen)getActivity()).loadFragment(TankEntryDetailViewFragment.newInstance(position, id, (TankEntry)parent.getAdapter().getItem(position)));
    }
}
