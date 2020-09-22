package com.nolari.benzinverbrauchberechner.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nolari.benzinverbrauchberechner.R;
import com.nolari.benzinverbrauchberechner.database.DatabaseSingleton;
import com.nolari.benzinverbrauchberechner.database.TankEntry;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DetailViewFragment extends Fragment{

    int uid = -1;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

    public DetailViewFragment(int uid){
        this.uid = uid;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detailview, container, false);

        TankEntry tankEntry = DatabaseSingleton.getInstance(getContext()).getTankEntryDao().getEntry(uid);

        TextView tvHeader = v.findViewById(R.id.detailViewHeader);
        TextView tvLitres = v.findViewById(R.id.detailView_litres);
        TextView tvPpl = v.findViewById(R.id.detailView_pricePerLitre);
        TextView tvKilometres = v.findViewById(R.id.detailView_kilometres);
        TextView tvTripmeter = v.findViewById(R.id.detailView_tripmeter);
        TextView tvConsumption = v.findViewById(R.id.detailView_fuelConsumption);

        tvHeader.setText(sdf.format(tankEntry.getDate()));
        tvLitres.setText(formatFloat2Decimals(tankEntry.getLitres(), "l"));
        tvPpl.setText(formatFloat3Decimals(tankEntry.getPricePerLitre(), "€/l"));
        tvKilometres.setText(formatFloat1Decimal(tankEntry.getKilometres(), "km"));
        tvTripmeter.setText(formatFloat1Decimal(tankEntry.getTripmeter(), "km"));
        tvConsumption.setText(formatFloat2Decimals(tankEntry.getFuelConsumption(), "l/100km"));

        if(tankEntry.getNotes() == null || tankEntry.getNotes().isEmpty()){
            v.findViewById(R.id.tableRowNotes).setVisibility(View.GONE);
        }else{
            TextView tvNote = v.findViewById(R.id.detailView_notes);
            tvNote.setText(tankEntry.getNotes());
        }

        return v;
    }

    private String formatFloat2Decimals(float toFormat, String unit) {
        return String.format(Locale.GERMAN, "%.2f", toFormat) + " " + unit;
    }
    private String formatFloat1Decimal(float toFormat, String unit) {
        return String.format(Locale.GERMAN, "%.1f", toFormat) + " " + unit;
    }
    private String formatFloat3Decimals(float toFormat, String unit) {
        return String.format(Locale.GERMAN, "%.3f", toFormat) + " " + unit;
    }

}
