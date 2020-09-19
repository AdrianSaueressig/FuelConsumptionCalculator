package com.nolari.benzinverbrauchberechner.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.nolari.benzinverbrauchberechner.R;
import com.nolari.benzinverbrauchberechner.fragment.DetailViewFragment;

public class DetailViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tank_entry_detail_view);

        Toolbar toolbar =
                (Toolbar) findViewById(R.id.top_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        int uid = intent.getIntExtra("TANKENTRY_UID", -1);
        if(uid == -1){
            return; // TODO display error?
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.detailview_container, new DetailViewFragment(uid))
                .commit();
    }
}
