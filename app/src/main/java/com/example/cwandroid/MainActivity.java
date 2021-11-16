package com.example.cwandroid;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    EditText bedRoom, rentPrice, notes, reporter;
    Spinner propertyType, furnitureType;
    TextView date;
    Button chooseDate;
    Calendar calendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         propertyType = findViewById(R.id.propertyType);
        final ArrayList<String> properties = new ArrayList<>();
        properties.add("Choose One Properties");
        properties.add("Flat");
        properties.add("Apartment");
        properties.add("Home");
        properties.add("Bungalow");

        ArrayAdapter<String> propertyAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item,properties) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textview = (TextView) view;
                if (position == 0) {
                    textview.setTextColor(Color.GRAY);
                } else {
                    textview.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        propertyAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        propertyType.setAdapter(propertyAdapter);
        propertyType.setSelection(0);
        final ArrayList<String> furnitures = new ArrayList<>();
        furnitures.add("Choose One Furniture (Not required)");
        furnitures.add("Furnished");
        furnitures.add("Half Furnished");
        furnitures.add("Unfurnished");

        ArrayAdapter<String> furnitureAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item,furnitures) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textview = (TextView) view;
                if (position == 0) {
                    textview.setTextColor(Color.GRAY);
                } else {
                    textview.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        //furniture spinner
        furnitureType = findViewById(R.id.furnitureType);
        furnitureType.setAdapter(furnitureAdapter);
        furnitureType.setSelection(0);
        //edit text
        bedRoom = findViewById(R.id.bedRoom);
        rentPrice = findViewById(R.id.rentPrice);
        notes = findViewById(R.id.note);
        reporter = findViewById(R.id.reporter);
        //date
        date = findViewById(R.id.date);
        String dateFormat = "dd/MM/yyyy";
        SimpleDateFormat format = new SimpleDateFormat(dateFormat, Locale.US);
        date.setText(format.format(new Date()));

        date = findViewById(R.id.date);

        DatePickerDialog.OnDateSetListener datePicker = (view, year, monthOfYear, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            date.setText(format.format(calendar.getTime()));
        };

        chooseDate = findViewById(R.id.chooseDate);
        chooseDate.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, datePicker, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
            datePickerDialog.show();
        });

        //submit button
        Button submit = findViewById(R.id.submit);
        submit.setOnClickListener(view -> {
            boolean check = checkBeforeSubmit();
            if(check == true) {
                String furniture;
                if(furnitureType.getSelectedItem().toString().equals("Choose One Furniture (Not required)")) {
                    furniture = "";
                }
                else {
                    furniture = furnitureType.getSelectedItem().toString();
                }
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Preview")
                        .setMessage(Html.fromHtml("Property Name: " + "<b>" + propertyType.getSelectedItem().toString() + "</b>" + "<br>" +
                                "Bed Room: "+ "<b>" + bedRoom.getText() + "</b>" + "<br>" +
                                "Date: " + "<b>" + date.getText() + "</b>" + "<br>" +
                                "Rent Price: "+ "<b>" + rentPrice.getText() + "</b>" + "<br>" +
                                "Furniture Type: " + "<b>" + furniture + "</b>" + "<br>" +
                                "Notes: " + "<b>" + notes.getText() + "</b>" + "<br>" +
                                "Reporter: " + "<b>" + reporter.getText() + "</b>"))
                        .setPositiveButton("Back To Edit", null).show();
            }
        });
    }

    private boolean checkBeforeSubmit() {
        if(propertyType.getSelectedItem().toString().equals("Choose One Properties")) {
            TextView error = (TextView) propertyType.getSelectedView();
            error.setError("Please select a valid property type before submit");
            return false;
        }
        if(bedRoom.length() <= 0) {
            bedRoom.setError("Bedroom is required");
            return false;

        }
        else if(Integer.parseInt(String.valueOf(bedRoom.getText())) < 1) {
            bedRoom.setError("Bedroom must greater or equal 1");
            return false;

        }
        if(rentPrice.length() <=0) {
            rentPrice.setError("Rent price is required");
            return false;

        }
        else if(Double.parseDouble(String.valueOf(rentPrice.getText())) < 0){
            rentPrice.setError("Rent price must greater or equal 0");
            return false;

        }
        if(reporter.length() <= 0) {
            reporter.setError("Reporter name is required");
            return false;

        }
        return true;
    }
}