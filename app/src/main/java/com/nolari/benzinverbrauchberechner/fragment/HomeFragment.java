package com.nolari.benzinverbrauchberechner.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nolari.benzinverbrauchberechner.R;
import com.nolari.benzinverbrauchberechner.database.DatabaseSingleton;
import com.nolari.benzinverbrauchberechner.database.TankEntryDao;

import java.io.File;
import java.util.Locale;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_home, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        resolveMainImage();

        TankEntryDao dao = DatabaseSingleton.getInstance(getContext()).getTankEntryDao();
        resolveAverageLitres(view, dao.getAverageLitres());
        resolveAveragePricePerLitres(view, dao.getAveragePricePerLitre());
        resolveAverageTripmeter(view, dao.getAverageTripmeter());
        resolveAverageConsumption(view, dao.getAverageConsumption());
    }

    private void resolveAverageConsumption(View view, float averageConsumption) {
        TextView tv = view.findViewById(R.id.avgConsumption);
        tv.setText(String.format(Locale.GERMAN, "%.2f", averageConsumption) + " l/100km");
    }

    private void resolveAverageTripmeter(View view, float avgTripmeter) {
        TextView tv = view.findViewById(R.id.avgTripmeter);
        tv.setText(String.format(Locale.GERMAN, "%.2f", avgTripmeter) + " km");
    }

    private void resolveAveragePricePerLitres(View view, float avgPpl) {
        TextView tv = view.findViewById(R.id.avgPricePerLitre);
        tv.setText(String.format(Locale.GERMAN, "%.2f", avgPpl) + " €/l");
    }

    private void resolveAverageLitres(View view, float avgLitres) {
        TextView tv = view.findViewById(R.id.avgLitres);
        tv.setText(String.format(Locale.GERMAN, "%.2f", avgLitres) + " l");
    }

    private void resolveMainImage() {
        File file = new File(getContext().getFilesDir() + "/homescreenimage.jpg");
        if(file.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
            ImageView imageView = getActivity().findViewById(R.id.imageView);
            imageView.setImageBitmap(bitmap);
        }
    }
}
