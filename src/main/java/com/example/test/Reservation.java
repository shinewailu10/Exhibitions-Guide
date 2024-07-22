package com.example.test;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Reservation extends AppCompatActivity {

    private Spinner daySpinner;
    private Spinner timeSpinner;
    private EditText numVisitorsEditText;
    private Spinner hallSpinner;
    private TextView totalChargesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        Intent intent = getIntent();
        String selectedDay = intent.getStringExtra("day");
        String selectedTime = intent.getStringExtra("time");
        int numVisitors = intent.getIntExtra("numVisitors", 1); // Default value is 1
        String venue = intent.getStringExtra("artGalleryValue");





        // Initialize  views
        daySpinner = findViewById(R.id.spinnerDay);
        timeSpinner = findViewById(R.id.spinnerTime);
        hallSpinner = findViewById(R.id.spinnerHall);
        numVisitorsEditText = findViewById(R.id.editTextNumVisitors);
        numVisitorsEditText = findViewById(R.id.editTextNumVisitors);
        totalChargesTextView = findViewById(R.id.textViewTotalCharges);
        // Set default value for numVisitorsEditText
        hallSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get selected hall option
                String selectedHall = parent.getItemAtPosition(position).toString();

                // Update timeSpinner based on selected hall
                if (selectedHall.equals("Visual Show")) {
                    populateSpinners2(selectedDay, selectedTime);

        } else {
                    populateSpinners(selectedDay, selectedTime);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle no selection
            }


});
        numVisitorsEditText.setText(String.valueOf(numVisitors)); // Set numVisitorsEditText

        String[] hall = {"Art Gallery", "WWI Exhibition", "Exploring the Space", "Visual Show"};
        ArrayAdapter<String> hallAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, hall);
        hallAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hallSpinner.setAdapter(hallAdapter);
        hallSpinner.setSelection(getIndex(hallSpinner, venue));


        String selectedHall = hallSpinner.getSelectedItem().toString();
        Button calculateButton = findViewById(R.id.buttonCalculate);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateAndUpdateCharges();            }
        });
    }

    private void populateSpinners(String selectedDay, String selectedTime) {
        // Populate day spinner
        String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, daysOfWeek);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(dayAdapter);
        daySpinner.setSelection(getIndex(daySpinner, selectedDay));

        // Populate time spinner
        List<String> timeSlots = getTimeSlots();
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, timeSlots);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(timeAdapter);
        timeSpinner.setSelection(getIndex(timeSpinner, selectedTime));
    }

    private void populateSpinners2(String selectedDay, String selectedTime) {
        // Populate day spinner
        String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, daysOfWeek);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(dayAdapter);
        daySpinner.setSelection(getIndex(daySpinner, selectedDay));

        // Populate time spinner
        List<String> timeSlots2 = getTimeSlots2();
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, timeSlots2);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(timeAdapter);
        timeSpinner.setSelection(getIndex(timeSpinner, selectedTime));
    }

    private List<String> getTimeSlots2() {
        List<String> timeSlots = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 0);

        while (calendar.get(Calendar.HOUR_OF_DAY) < 21) {
            String startTime = getTimeString(calendar);

            // Add time slot
            timeSlots.add(startTime + " - " + getNextEndTime(calendar));


        }

        return timeSlots;
    }
    private String getNextEndTime(Calendar calendar) {
        // Get the hour and minute components
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Set the hour and minute components for the next session
        if (hour == 15) {
            hour += 2; // Increment by 2 hours for sessions at 15:00 and 17:00
        }
        else if (hour == 17) {
            hour += 2; // Increment by 2 hours for sessions at 15:00 and 17:00
        } else if (hour == 19) {
            hour = 21; // Set to 21:00 for the last session at 19:00
            minute = 0; // Reset minutes for the last session
        } else {
            hour++; // Increment by 1 hour for the session starting at 9:00
        }

        // Set the time in the calendar
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        // Return the formatted end time
        return getTimeString(calendar);
    }

    private int getIndex(Spinner spinner, String item) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(item)) {
                return i;
            }
        }
        return 0; // Default to the first item if not found
    }



    private float calculateCharges(String selectedDay, String selectedHall, int numVisitors) {
        float chargePerSession;

        // Determine charge per session based on selected day, time, and exhibition hall
        if (selectedDay.equals("Saturday") || selectedDay.equals("Sunday")) {
            // Weekend charges
            switch (selectedHall) {
                case "Art Gallery":
                    chargePerSession = 30;
                    break;
                case "WWI Exhibition":
                    chargePerSession = 25;
                    break;
                case "Exploring the Space":
                    chargePerSession = 35;
                    break;
                case "Visual Show":
                    chargePerSession = 40;
                    break;
                default:
                    chargePerSession = 0; // Invalid hall selected
                    break;
            }
        } else {
            // Weekday charges
            switch (selectedHall) {
                case "Art Gallery":
                    chargePerSession = 25;
                    break;
                case "WWI Exhibition":
                    chargePerSession = 20;
                    break;
                case "Exploring the Space":
                    chargePerSession = 30;
                    break;
                case "Visual Show":
                    chargePerSession = 40;
                    break;
                default:
                    chargePerSession = 0; // Invalid hall selected
                    break;
            }

        }
        chargePerSession=numVisitors * chargePerSession;


        // Apply 10% discount for 4+ visitors
        if (numVisitors > 4) {
            chargePerSession *= 0.9 ; // Apply 10% discount
        }
        return chargePerSession;
    }


    private void calculateAndUpdateCharges() {
        String selectedDay = daySpinner.getSelectedItem().toString();
        String selectedTime = timeSpinner.getSelectedItem().toString();
        String selectedHall = hallSpinner.getSelectedItem().toString();
        int numVisitors = Integer.parseInt(numVisitorsEditText.getText().toString());

        float totalCharges = calculateCharges(selectedDay, selectedHall, numVisitors);

        totalChargesTextView.setText(String.format(Locale.getDefault(), "Total Charges(AUD): $%.2f", totalCharges));
    }



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
}
