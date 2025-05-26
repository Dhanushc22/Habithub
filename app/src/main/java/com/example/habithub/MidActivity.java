
package com.example.habithub;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MidActivity extends AppCompatActivity {

    private ChipGroup habitChipGroup;
    private EditText timeInput;
    private Button logButton, nextButton;
    private LinearLayout loggedEntriesContainer;
    private HashMap<String, Integer> habitMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        habitChipGroup = findViewById(R.id.habitChipGroup);
        timeInput = findViewById(R.id.timeInput);
        logButton = findViewById(R.id.logButton);
        nextButton = findViewById(R.id.nextButton);
        loggedEntriesContainer = findViewById(R.id.loggedEntriesContainer);

        checkAndResetForNewDay();

        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logHabits();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (habitMap.isEmpty()) {
                    Toast.makeText(MidActivity.this, "Please log some habits first", Toast.LENGTH_SHORT).show();
                    return;
                }


                Intent intent = new Intent(MidActivity.this, EndActivity.class);
                Bundle bundle = new Bundle();
                for (String key : habitMap.keySet()) {
                    bundle.putInt(key, habitMap.get(key));
                }
                intent.putExtras(bundle);


                SharedPreferences prefs = getSharedPreferences("HabitPrefs", MODE_PRIVATE);
                intent.putExtra("yesterdayData", prefs.getString("yesterdayHabitData", ""));

                startActivity(intent);
            }
        });
    }

    private void logHabits() {
        String timeText = timeInput.getText().toString().trim();
        if (TextUtils.isEmpty(timeText)) {
            Toast.makeText(this, "Please enter time spent", Toast.LENGTH_SHORT).show();
            return;
        }

        int timeSpent;
        try {
            timeSpent = Integer.parseInt(timeText);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid time input", Toast.LENGTH_SHORT).show();
            return;
        }

        List<String> selectedHabits = new ArrayList<>();
        for (int i = 0; i < habitChipGroup.getChildCount(); i++) {
            Chip chip = (Chip) habitChipGroup.getChildAt(i);
            if (chip.isChecked()) {
                selectedHabits.add(chip.getText().toString());
            }
        }

        if (selectedHabits.isEmpty()) {
            Toast.makeText(this, "Please select at least one habit", Toast.LENGTH_SHORT).show();
            return;
        }

        for (String habit : selectedHabits) {
            TextView logEntry = new TextView(this);
            logEntry.setText("Habit: " + habit + " | Time Spent: " + timeSpent + " mins");
            logEntry.setTextSize(16f);
            logEntry.setPadding(8, 4, 8, 4);
            loggedEntriesContainer.addView(logEntry);
            habitMap.put(habit, timeSpent);
        }

        saveHabits();


        timeInput.setText("");
        for (int i = 0; i < habitChipGroup.getChildCount(); i++) {
            Chip chip = (Chip) habitChipGroup.getChildAt(i);
            chip.setChecked(false);
        }
    }

    private void checkAndResetForNewDay() {
        SharedPreferences prefs = getSharedPreferences("HabitPrefs", MODE_PRIVATE);
        long lastSavedTime = prefs.getLong("lastSavedTime", 0);


        long now = System.currentTimeMillis();
        Date nowDate = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        String todayStr = sdf.format(nowDate);


        String lastSavedDate = sdf.format(new Date(lastSavedTime));

        if (!todayStr.equals(lastSavedDate)) {

            String yesterdayData = prefs.getString("habitData", null);
            if (yesterdayData != null) {
                prefs.edit().putString("yesterdayHabitData", yesterdayData).apply();
            }


            prefs.edit().clear().apply();
            habitMap.clear();
            loggedEntriesContainer.removeAllViews();


            prefs.edit()
                    .putLong("lastSavedTime", now)
                    .apply();
        } else {
            loadSavedHabits();
        }
    }

    private void saveHabits() {
        SharedPreferences prefs = getSharedPreferences("HabitPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        JSONArray habitArray = new JSONArray();
        for (String habit : habitMap.keySet()) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("habit", habit);
                obj.put("time", habitMap.get(habit));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            habitArray.put(obj);
        }

        editor.putString("habitData", habitArray.toString());
        editor.apply();
    }

    private void loadSavedHabits() {
        SharedPreferences prefs = getSharedPreferences("HabitPrefs", MODE_PRIVATE);
        String habitJson = prefs.getString("habitData", null);

        if (habitJson != null) {
            try {
                JSONArray habitArray = new JSONArray(habitJson);
                for (int i = 0; i < habitArray.length(); i++) {
                    JSONObject obj = habitArray.getJSONObject(i);
                    String habit = obj.getString("habit");
                    int time = obj.getInt("time");

                    habitMap.put(habit, time);

                    TextView logEntry = new TextView(this);
                    logEntry.setText("Habit: " + habit + " | Time Spent: " + time + " mins");
                    logEntry.setTextSize(16f);
                    logEntry.setPadding(8, 4, 8, 4);
                    loggedEntriesContainer.addView(logEntry);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
