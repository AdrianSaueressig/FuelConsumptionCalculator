package com.nolari.benzinverbrauchberechner;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

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

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        loadFragment(new HomeFragment());
    }

    public boolean loadFragment(Fragment fragment){
        if(fragment == null){
            return false;
        }
        FragmentTransaction ft = getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment);
        ft.addToBackStack(null);
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
        TextView textViewResult = findViewById(R.id.showSavingResult);

        // read values
        if(inputTripmeter.getText().toString() == null || inputTripmeter.getText().toString().isEmpty() ||
            inputKilometres.getText().toString() == null || inputKilometres.getText().toString().isEmpty() ||
            inputLitres.getText().toString() == null || inputLitres.getText().toString().isEmpty() ||
            inputPricePerLitre.getText().toString() == null || inputPricePerLitre.getText().toString().isEmpty()){
            textViewResult.setText("Bitte in allen Feldern etwas eingeben vor dem Speichern!");
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
        textViewResult.setText("Eingaben wurden erfolgreich gespeichert! Der Verbrauch in l/km des letzten Tankens ist: " + fuelConsumption);
    }

    public void onDeleteClick(View button){
        TextView textViewResult = findViewById(R.id.showSavingResult);

        //do stuff with database
        TankDatabase database = DatabaseSingleton.getInstance(getApplicationContext());

        database.getTankEntryDao().deleteAll();
        textViewResult.setText("Alles gelöscht :)");
    }

    private float calculateFuelConsumption(int tripmeter, float litres) {
        return (100 * litres)/tripmeter;
    }
}
