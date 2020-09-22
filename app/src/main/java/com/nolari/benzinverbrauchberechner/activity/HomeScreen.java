package com.nolari.benzinverbrauchberechner.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.nolari.benzinverbrauchberechner.R;
import com.nolari.benzinverbrauchberechner.database.DatabaseSingleton;
import com.nolari.benzinverbrauchberechner.database.TankDatabase;
import com.nolari.benzinverbrauchberechner.database.TankEntry;
import com.nolari.benzinverbrauchberechner.fragment.HomeFragment;
import com.nolari.benzinverbrauchberechner.navigation.BottomNavigationManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HomeScreen extends AppCompatActivity{

    private static final int HAS_PICKED_IMAGE = 1;
    private static final String SDF = "dd.MM.yyyy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.top_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.inflateMenu(R.menu.toptoolbar_overflowmenu_homeactivity);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        BottomNavigationManager navManager = new BottomNavigationManager(getSupportFragmentManager());
        navigation.setOnNavigationItemSelectedListener(navManager);
        navManager.loadFragment(new HomeFragment());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toptoolbar_overflowmenu_homeactivity, menu);
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
        TextInputEditText inputDate = findViewById(R.id.input_datefield);

        SimpleDateFormat sdf = new SimpleDateFormat(SDF, Locale.GERMAN);

        // Check every mandatory field has data
        if(inputTripmeter.getText().toString().isEmpty() ||
            inputKilometres.getText().toString().isEmpty() ||
            inputLitres.getText().toString().isEmpty() ||
            inputPricePerLitre.getText().toString().isEmpty()||
            inputDate.getText().toString().isEmpty()){
            showToast("Bitte in allen Feldern etwas eingeben vor dem Speichern!");
            return;
        }

        float tripmeter = Float.parseFloat(inputTripmeter.getText().toString().replace(",", "."));
        float kilometers = Float.parseFloat(inputKilometres.getText().toString().replace(",", "."));
        float litres = Float.parseFloat(inputLitres.getText().toString().replace(",", "."));
        float pricePerLitre = Float.parseFloat(inputPricePerLitre.getText().toString().replace(",", "."));
        float fuelConsumption = 0;
        Date date = null;
        try {
            date = sdf.parse(inputDate.getText().toString());
        } catch (ParseException e) {
            date = new Date();
        }
        String notes = inputNotes.getText().toString();

        //do stuff with database
        TankDatabase database = DatabaseSingleton.getInstance(getApplicationContext());

        // edit old entry
        TankEntry latestEntry = database.getTankEntryDao().getLastEntry(kilometers);

        // create new database entry
        TankEntry newEntry = new TankEntry();
        newEntry.setDate(date);
        newEntry.setKilometres(kilometers);
        newEntry.setLitres(litres);
        newEntry.setPricePerLitre(pricePerLitre);
        newEntry.setNotes(notes);

        float oldTripmeter = 0;
        if(latestEntry!=null && latestEntry.getTripmeter() > 0){
            oldTripmeter = latestEntry.getTripmeter();
            newEntry.setTripmeter(oldTripmeter);
            recalculateOlderTankEntry(newEntry, oldTripmeter);
        }

        database.getTankEntryDao().insertAll(newEntry);
        recalculateOlderTankEntry(latestEntry, tripmeter);

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
        String fuelConsLastTank = "Noch kein Wert";
        if(latestEntry != null){
            fuelConsLastTank = String.format(Locale.GERMAN, "%.2f", latestEntry.getFuelConsumption());
        }
        showToast("Eingaben wurden erfolgreich gespeichert! Der Verbrauch in l/100km des letzten Tankens ist: " + fuelConsLastTank);
    }

    private void recalculateOlderTankEntry(TankEntry oldEntry, float newTripmeter) {
        if(oldEntry==null) {
            return;
        }
        oldEntry.setFuelConsumption(calculateFuelConsumption(newTripmeter, oldEntry.getLitres()));
        oldEntry.setTripmeter(newTripmeter);
        DatabaseSingleton.getInstance(getApplicationContext()).getTankEntryDao().updateTankEntry(oldEntry);
    }

    private void showToast(String text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void onDatePickerClick(final View datePickerElement){
        final Calendar cal = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, monthOfYear);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String dateString = convertToDateString(cal);

                ((EditText)findViewById(R.id.input_datefield)).setText(dateString);
            }

        };

        DatePickerDialog datePicker = new DatePickerDialog(
                this,
                dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH));
        datePicker.show();
    }

    public void onMainImageClick(View mainImageContainer){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Bild auswählen..."), HAS_PICKED_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == HAS_PICKED_IMAGE && resultCode == Activity.RESULT_OK) {
            if(data == null){
                return;
            }

            try {
                InputStream inputStream = this.getContentResolver().openInputStream(data.getData());
                FileOutputStream outputStream = new FileOutputStream(new File(getFilesDir()+"/homescreenimage.jpg"));
                byte[] buf = new byte[1024];
                int len;
                while((len=inputStream.read(buf))>0){
                    outputStream.write(buf,0,len);
                }
                outputStream.close();
                inputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return;
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            Bitmap bitmap = BitmapFactory.decodeFile(getFilesDir() + "/homescreenimage.jpg");
            ((ImageView)findViewById(R.id.imageView)).setImageBitmap(bitmap);
        }
    }

    private String convertToDateString(Calendar cal) {
        SimpleDateFormat sdf = new SimpleDateFormat(SDF, Locale.GERMAN);
        return sdf.format(cal.getTime());
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
