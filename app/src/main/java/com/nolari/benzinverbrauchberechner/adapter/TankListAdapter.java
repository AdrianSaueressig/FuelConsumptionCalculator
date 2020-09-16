package com.nolari.benzinverbrauchberechner.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nolari.benzinverbrauchberechner.R;
import com.nolari.benzinverbrauchberechner.database.TankEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Nolari on 03.03.2018.
 */

public class TankListAdapter extends ArrayAdapter<TankEntry> {

    private Context context;
    int resource;

    public TankListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<TankEntry> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        TextView dateTV = convertView.findViewById(R.id.listItemDate);
        TextView fcTV = convertView.findViewById(R.id.listItemConsumption);
        TextView litresTV = convertView.findViewById(R.id.listItemLitres);
        TextView tripmeterTV = convertView.findViewById(R.id.listItemTripmeter);

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);

        TankEntry entry = getItem(position);
        dateTV.setText(sdf.format(entry.getDate()));
        fcTV.setText(String.format("%.2f", entry.getFuelConsumption()) + " l/100km");
        litresTV.setText(String.format("%.2f", entry.getLitres()) + " l");
        tripmeterTV.setText(String.format("%.2f", entry.getTripmeter()) + " km");

        return convertView;
    }
}
