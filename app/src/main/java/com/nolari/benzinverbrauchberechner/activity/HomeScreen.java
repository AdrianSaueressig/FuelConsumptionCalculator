package com.nolari.benzinverbrauchberechner.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nolari.benzinverbrauchberechner.R;
import com.nolari.benzinverbrauchberechner.database.DatabaseSingleton;
import com.nolari.benzinverbrauchberechner.database.TankDatabase;
import com.nolari.benzinverbrauchberechner.database.TankEntry;
import com.nolari.benzinverbrauchberechner.fragment.GraphFragment;
import com.nolari.benzinverbrauchberechner.fragment.HomeFragment;
import com.nolari.benzinverbrauchberechner.fragment.NewEntryFragment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeScreen extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    Fragment currentFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.top_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.inflateMenu(R.menu.toptoolbar);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        loadFragment(new HomeFragment());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toptoolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() == R.id.action_settings) {
            startSettings();
            return true;
        }else{
            return super.onOptionsItemSelected(item);
        }
    }

    private void startSettings(){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public boolean loadFragment(Fragment fragment){
        if(fragment == null){
            return false;
        }
        FragmentTransaction ft = getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment);
        if(currentFragment!=null){
            //not on startup
            ft.addToBackStack(null);
        }
        ft.commit();
        currentFragment = fragment;
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch(item.getItemId()){
            case R.id.navigation_home:
                if(!(currentFragment instanceof HomeFragment)){
                    fragment= new HomeFragment();
                }
                break;
            case R.id.navigation_new_entry:
                if(!(currentFragment instanceof NewEntryFragment)){
                    fragment = new NewEntryFragment();
                }
                break;
            case R.id.navigation_history:
                if(!(currentFragment instanceof GraphFragment)){
                    fragment = new GraphFragment();
                }
                break;
        }

        return loadFragment(fragment);
    }

    public void onSaveClick(View button){
        TextInputEditText inputTripmeter = findViewById(R.id.input_tripmeter);
        TextInputEditText inputKilometres = findViewById(R.id.input_kilometers);
        TextInputEditText inputLitres = findViewById(R.id.input_litres);
        TextInputEditText inputPricePerLitre = findViewById(R.id.input_pricePerLitre);
        ProgressBar progressBar = findViewById(R.id.progressBar);

        // read values
        if(inputTripmeter.getText().toString().isEmpty() ||
            inputKilometres.getText().toString().isEmpty() ||
            inputLitres.getText().toString().isEmpty() ||
            inputPricePerLitre.getText().toString().isEmpty()){
            showToast("Bitte in allen Feldern etwas eingeben vor dem Speichern!");
            return;
        }
        int tripmeter = Integer.parseInt(inputTripmeter.getText().toString());
        int kilometers = Integer.parseInt(inputKilometres.getText().toString());
        float litres = Float.parseFloat(inputLitres.getText().toString().replace(",", "."));
        float pricePerLitre = Float.parseFloat(inputPricePerLitre.getText().toString().replace(",", "."));
        float fuelConsumption = 0;

        //do stuff with database
        progressBar.setVisibility(View.VISIBLE);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        TankDatabase database = DatabaseSingleton.getInstance(getApplicationContext());

        // edit old entry
        TankEntry latestEntry = database.getTankEntryDao().getLastEntry();
        if(latestEntry!=null){
            fuelConsumption = calculateFuelConsumption(tripmeter, latestEntry.getLitres());
            latestEntry.setFuelConsumption(fuelConsumption);
            latestEntry.setTripmeter(tripmeter);
            database.getTankEntryDao().updateTankEntry(latestEntry);
        }

        // create new database entry
        TankEntry newEntry = new TankEntry();
        newEntry.setDate(sdf.format(new Date()));
        newEntry.setKilometres(kilometers);
        newEntry.setLitres(litres);
        newEntry.setPricePerLitre(pricePerLitre);
        database.getTankEntryDao().insertAll(newEntry);

        //output
        progressBar.setVisibility(View.GONE);

        showToast("Eingaben wurden erfolgreich gespeichert! Der Verbrauch in l/100km des letzten Tankens ist: " + fuelConsumption);
    }

    private void showToast(String text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void onDeleteClick(View button){
        //do stuff with database
        TankDatabase database = DatabaseSingleton.getInstance(getApplicationContext());

        database.getTankEntryDao().deleteAll();
        showToast("Alles gelöscht :)");
    }

    private float calculateFuelConsumption(int tripmeter, float litres) {
        return (100 * litres)/tripmeter;
    }
}
