package com.nolari.benzinverbrauchberechner.fragment;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nolari.benzinverbrauchberechner.R;

import java.io.File;

/**
 * Created by Nolari on 02.03.2018.
 */

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_home, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        File file = new File(getContext().getFilesDir() + "/homescreenimage.jpg");
        if(file.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
            ImageView imageView = getActivity().findViewById(R.id.imageView);
            imageView.setImageBitmap(bitmap);
        }

    }
}
