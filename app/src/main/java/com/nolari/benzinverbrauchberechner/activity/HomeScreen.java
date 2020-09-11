package com.nolari.benzinverbrauchberechner.activity;

import android.app.Activity;
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
import android.view.inputmethod.InputMethodManager;
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
import com.nolari.benzinverbrauchberechner.navigation.BottomNavigationManager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeScreen extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.top_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.inflateMenu(R.menu.toptoolbar);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        BottomNavigationManager navManager = new BottomNavigationManager(getSupportFragmentManager());
        navigation.setOnNavigationItemSelectedListener(navManager);
        navManager.loadFragment(new HomeFragment());
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

    public void onSaveClick(View button){
        TextInputEditText inputTripmeter = findViewById(R.id.input_tripmeter);
        TextInputEditText inputKilometres = findViewById(R.id.input_kilometers);
        TextInputEditText inputLitres = findViewById(R.id.input_litres);
        TextInputEditText inputPricePerLitre = findViewById(R.id.input_pricePerLitre);
        TextInputEditText inputNotes = findViewById(R.id.input_notes);
        ProgressBar progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.VISIBLE);

        // read values
        if(inputTripmeter.getText().toString().isEmpty() ||
            inputKilometres.getText().toString().isEmpty() ||
            inputLitres.getText().toString().isEmpty() ||
            inputPricePerLitre.getText().toString().isEmpty()){
            showToast("Bitte in allen Feldern etwas eingeben vor dem Speichern!");
            return;
        }
        float tripmeter = Float.parseFloat(inputTripmeter.getText().toString().replace(",", "."));
        float kilometers = Float.parseFloat(inputKilometres.getText().toString().replace(",", "."));
        float litres = Float.parseFloat(inputLitres.getText().toString().replace(",", "."));
        float pricePerLitre = Float.parseFloat(inputPricePerLitre.getText().toString().replace(",", "."));
        float fuelConsumption = 0;
        String notes = inputNotes.getText().toString();

        //do stuff with database
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
        newEntry.setNotes(notes);
        database.getTankEntryDao().insertAll(newEntry);

        // clear fields
        inputLitres.setText("");
        inputPricePerLitre.setText("");
        inputTripmeter.setText("");
        inputKilometres.setText("");
        inputNotes.setText("");

        //hide keyboard
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = this.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

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

    private float calculateFuelConsumption(float tripmeter, float litres) {
        return (100 * litres)/tripmeter;
    }
}
