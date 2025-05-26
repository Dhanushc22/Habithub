package com.example.habithub;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class EndActivity extends AppCompatActivity {

    private LinearLayout progressContainer;
    private LineChart    lineChart;
    private TextView     habitCountText, totalTimeText, weeklyTotalValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        progressContainer  = findViewById(R.id.progressContainer);
        lineChart          = findViewById(R.id.lineChart);
        habitCountText     = findViewById(R.id.habitCountText);
        totalTimeText      = findViewById(R.id.totalTimeText);
        weeklyTotalValue   = findViewById(R.id.weeklyTotalValue);

        // Retrieve “today” and “yesterday” data
        Bundle bundle = getIntent().getExtras();
        HashMap<String, Integer> todayData     = new HashMap<>();
        String yesterdayJson                   = getIntent().getStringExtra("yesterdayData");
        HashMap<String, Integer> yesterdayData = parseHabitJson(yesterdayJson);

        if (bundle != null) {
            for (String key : bundle.keySet()) {
                if (!key.equals("yesterdayData")) {
                    int value = bundle.getInt(key, 0);
                    todayData.put(key, value);
                }
            }
        }

        displayHabitProgress(todayData);
        setupWaveGraph(todayData, yesterdayData);
        updateSummary(todayData);
    }

    private void displayHabitProgress(HashMap<String, Integer> habits) {
        int[] colors = {
                Color.parseColor("#9C27B0"),
                Color.parseColor("#607D8B"),
                Color.parseColor("#F44336"),
                Color.parseColor("#3F51B5"),
                Color.parseColor("#03A9F4"),
                Color.parseColor("#009688"),
                Color.parseColor("#4CAF50"),
                Color.parseColor("#FF9800"),
                Color.parseColor("#795548"),
                Color.parseColor("#E91E63")
        };

        int colorIndex = 0;

        for (String habit : habits.keySet()) {
            int time = habits.get(habit);
            int color = colors[colorIndex % colors.length];
            colorIndex++;

            TextView label = new TextView(this);
            label.setText(habit);
            label.setTextSize(16f);
            label.setTextColor(color);
            label.setPadding(0, 8, 0, 4);

            ProgressBar progressBar = new ProgressBar(this, null,
                    android.R.attr.progressBarStyleHorizontal);
            progressBar.setMax(100);
            progressBar.setProgress(Math.min(time, 100));
            progressBar.setProgressTintList(
                    android.content.res.ColorStateList.valueOf(color));
            progressBar.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));

            progressContainer.addView(label);
            progressContainer.addView(progressBar);
        }
    }

    private void setupWaveGraph(HashMap<String, Integer> today,
                                HashMap<String, Integer> yesterday) {
        ArrayList<Entry> todayEntries     = new ArrayList<>();
        ArrayList<Entry> yesterdayEntries = new ArrayList<>();
        final ArrayList<String> labels    = new ArrayList<>();

        int index = 0;
        for (String habit : today.keySet()) {
            int todayVal     = today.get(habit);
            int yesterdayVal = yesterday.getOrDefault(habit, 0);

            todayEntries.add(new Entry(index, todayVal));
            yesterdayEntries.add(new Entry(index, yesterdayVal));
            labels.add(habit);
            index++;
        }


        LineDataSet todayDataSet = new LineDataSet(todayEntries, "Today");
        todayDataSet.setColor(Color.parseColor("#42A5F5"));
        todayDataSet.setLineWidth(2f);
        todayDataSet.setCircleColor(Color.parseColor("#1E88E5"));
        todayDataSet.setCircleRadius(4f);


        todayDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        todayDataSet.setCubicIntensity(0.3f);

        todayDataSet.setDrawFilled(true);
        todayDataSet.setFillColor(Color.parseColor("#90CAF9"));
        todayDataSet.setFillAlpha(200);

        LineDataSet yesterdayDataSet = new LineDataSet(yesterdayEntries, "Yesterday");
        yesterdayDataSet.setColor(Color.parseColor("#B0BEC5"));
        yesterdayDataSet.setLineWidth(2f);
        yesterdayDataSet.setCircleColor(Color.parseColor("#78909C"));
        yesterdayDataSet.setCircleRadius(4f);

        yesterdayDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        yesterdayDataSet.setCubicIntensity(0.3f);
        yesterdayDataSet.setDrawFilled(false);


        LineData data = new LineData(todayDataSet, yesterdayDataSet);
        data.setDrawValues(false);

        lineChart.setData(data);


        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new com.github.mikephil.charting.formatter.ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int i = Math.round(value);
                return (i >= 0 && i < labels.size()) ? labels.get(i) : "";
            }
        });


        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setDrawGridLines(true);
        lineChart.getAxisRight().setEnabled(false);

        // Disable description text
        lineChart.getDescription().setEnabled(false);
        lineChart.getLegend().setEnabled(true);
        lineChart.animateX(800);

        lineChart.invalidate();
    }

    private void updateSummary(HashMap<String, Integer> habits) {
        int totalHabits = habits.size();
        int totalTime   = 0;
        for (int time : habits.values()) {
            totalTime += time;
        }

        habitCountText.setText("Habits Logged: " + totalHabits);
        totalTimeText.setText("Total Time: " + totalTime + " min");
        weeklyTotalValue.setText(totalTime + " mins");
    }

    private HashMap<String, Integer> parseHabitJson(String json) {
        HashMap<String, Integer> map = new HashMap<>();
        if (json != null && !json.isEmpty()) {
            try {
                JSONArray array = new JSONArray(json);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    String habit = obj.getString("habit");
                    int time     = obj.getInt("time");
                    map.put(habit, time);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
