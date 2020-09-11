package com.nolari.benzinverbrauchberechner.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.nolari.benzinverbrauchberechner.R;
import com.nolari.benzinverbrauchberechner.database.TankEntry;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TankEntryDetailViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TankEntryDetailViewFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_POSITION = "position";
    private static final String ARG_ID = "id";

    // TODO: Rename and change types of parameters
    private int position;
    private long id;
    private TankEntry entry;

    public TankEntryDetailViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param position position of listitem.
     * @param id id of listitem.
     * @return A new instance of fragment TankEntryDetailViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TankEntryDetailViewFragment newInstance(int position, long id, TankEntry entry) {
        TankEntryDetailViewFragment fragment = new TankEntryDetailViewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        args.putLong(ARG_ID, id);
        fragment.entry = entry;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(ARG_POSITION);
            id = getArguments().getLong(ARG_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tank_entry_detail_view, container, false);
        TextView tv = v.findViewById(R.id.tankEntryDetailTextView);
        tv.setText(entry.toString());

        return v;
    }
}
