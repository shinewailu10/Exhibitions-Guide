package com.example.test;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ArtGallery extends AppCompatActivity {

    private Spinner daySpinner;
    private Spinner timeSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_art_gallery);

        daySpinner = findViewById(R.id.spinner1);
        timeSpinner = findViewById(R.id.spinner2);

        // Populate day Spinner with options from Monday to Sunday
        String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, daysOfWeek);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(dayAdapter);

        // Set default day to current day
        Calendar calendar = Calendar.getInstance();
        int currentDayIndex = calendar.get(Calendar.DAY_OF_WEEK) - 2; // Adjusted for 0-based index and starting from Monday
        if (currentDayIndex < 0) {
            currentDayIndex = 6; // Wrap around to Sunday if current day is Monday
        }

        // Populate time Spinner with time slots
        List<String> timeSlots = getTimeSlots();
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, timeSlots);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(timeAdapter);

        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);

        // Round the current time to the nearest half-hour interval
        if (currentMinute <= 30) {
            currentMinute = 30;
        } else if (currentHour>19) {
            currentDayIndex++;
        } else {
            currentMinute = 60 ;
        }

        // Set the initial starting time in the calendar
        calendar.set(Calendar.HOUR_OF_DAY, currentHour);
        calendar.set(Calendar.MINUTE, currentMinute);

        // Add time slots starting from the nearest half-hour interval before the default starting time
        // until the end time (9:00 PM)
            String startTime = getTimeString(calendar);

            // Add 1.5 hours to the current time to get the end time
            calendar.add(Calendar.MINUTE, 90);
            String endTime = getTimeString(calendar);

            String timeSlot = startTime + " - " + endTime;
        String defaultTimeSlot = timeSlot;
        int defaultTimeSlotIndex = timeSlots.indexOf(defaultTimeSlot);
        timeSpinner.setSelection(defaultTimeSlotIndex);
        daySpinner.setSelection(currentDayIndex);



        // Set listener for calculateButton
        Button resvbtn = findViewById(R.id.resv);
        resvbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToReservation();
            }
        });

    }

    // Method to generate time slots and corresponding end times
    // Method to generate time slots and corresponding end times
    private List<String> getTimeSlots() {
        List<String> timeSlots = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);

// Round the current time to the nearest half-hour interval
        if (currentMinute < 30) {
            currentMinute = 0;
        } else {
            currentMinute = 30;
        }

        // Set the initial starting time in the calendar
        calendar.set(Calendar.HOUR_OF_DAY, currentHour);
        calendar.set(Calendar.MINUTE, currentMinute);

        // Add time slots starting from the nearest half-hour interval before the default starting time
        // until the end time (9:00 PM)


        // Add time slots starting from 9:00 AM and corresponding end times
        for (int hour = 9; hour <= 19; hour++) {
            for (int minute = 0; minute < 60; minute += 30) {
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                String startTime = getTimeString(calendar);

                calendar.add(Calendar.MINUTE, 90); // Add 1.5 hours
                String endTime = getTimeString(calendar);

                String timeSlot = startTime + " - " + endTime;
                timeSlots.add(timeSlot);

                // Exit loop if last time slot reached (7:30 PM)
                if (hour == 19 && minute == 30) {
                    break;
                }
            }
        }



        return timeSlots;
    }

    // Helper method to format time as HH:mm
    private String getTimeString(Calendar calendar) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(calendar.getTime());
    }



    private void goToReservation() {
        Spinner daySpinner = findViewById(R.id.spinner1);
        Spinner timeSpinner = findViewById(R.id.spinner2);
        String selectedDay = daySpinner.getSelectedItem().toString();
        String selectedTime = timeSpinner.getSelectedItem().toString();
        EditText numEditText = findViewById(R.id.num);
        int numVisitors;
        try {
            // Attempt to convert the input string to a numeric value
            numVisitors = Integer.parseInt(numEditText.getText().toString());

            // Perform calculations or other operations with the numeric value
            // ...

        } catch (NumberFormatException e) {
            // Handle the case where the input string is empty or not a valid integer
            // Display an error message to the user, or provide a default value
            // For example:
            numVisitors = 0; // Set a default value
            // Or display an error message:
            Toast.makeText(getApplicationContext(), "Invalid number of visitors", Toast.LENGTH_SHORT).show();
        }
        // Get the value of the TextView in Art Gallery activity
        TextView textView = findViewById(R.id.t1);
        String artGalleryValue = textView.getText().toString();

        Intent intent = new Intent(ArtGallery.this, Reservation.class);
        intent.putExtra("day", selectedDay);
        intent.putExtra("time", selectedTime);
        intent.putExtra("numVisitors", numVisitors);
        intent.putExtra("artGalleryValue", artGalleryValue); // Add the hall value to extras
        startActivity(intent);
    }



}

