package com.nolari.benzinverbrauchberechner.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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

        setupToolbar();

        Intent intent = getIntent();
        int uid = intent.getIntExtra("TANKENTRY_UID", -1);
        if(uid == -1){
            showToast("Es ist ein Fehler aufgetreten beim Anzeigen der Details. Bitte erstelle einen Bugreport in den Einstellungen.");
            return;
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.detailview_container, new DetailViewFragment(uid))
                .commit();
    }

    private void showToast(String text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private void setupToolbar() {
        Toolbar toolbar =
                (Toolbar) findViewById(R.id.top_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.inflateMenu(R.menu.toptoolbar_overflowmenu_detailviewactivity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toptoolbar_overflowmenu_detailviewactivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()){
            case R.id.action_delete:
            case R.id.action_edit:
                showToast("Noch nicht implementiert.");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
