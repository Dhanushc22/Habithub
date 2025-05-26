package com.example.habithub;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class StartActivity extends AppCompatActivity {

    private View circle1, circle2;
    private TextView heroTitle, heroSubtitle, streakText, quoteText;
    private ImageView heroImage;
    private Button btnExploreFeatures, btnGetStarted;
    private ScrollView scrollView;
    private View featuresHeading, ctaSection;

    private final List<String> dailyQuotes = Arrays.asList(
            "“Small wins. Big growth.”",
            "“You're not late. You're right on time for change.”",
            "“Habits shape your story — one line at a time.”",
            "“Today is a vote for who you’re becoming.”",
            "“Discipline is a quiet superpower.”",
            "“One more day stronger.”",
            "“Your future self says thank you.”",
            "“Beating your yesterday. One habit at a time.”",
            "“What you do daily is who you become.”",
            "“Less scrolling. More becoming.”"
    );

    @SuppressLint("MissingInflatedId")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        scrollView          = findViewById(R.id.scroll_view_root);
        circle1             = findViewById(R.id.circle1);
        circle2             = findViewById(R.id.circle2);
        heroTitle           = findViewById(R.id.hero_title);
        heroSubtitle        = findViewById(R.id.hero_subtitle);
        heroImage           = findViewById(R.id.hero_image);
        btnExploreFeatures  = findViewById(R.id.btn_explore_features);
        btnGetStarted       = findViewById(R.id.btn_get_started);
        featuresHeading     = findViewById(R.id.tv_features_heading);
        ctaSection          = findViewById(R.id.cta_section);
        streakText          = findViewById(R.id.streakText);
        quoteText           = findViewById(R.id.quoteText);


        Animation floatAnim = AnimationUtils.loadAnimation(this, R.anim.float1);
        Animation fadeInUp  = AnimationUtils.loadAnimation(this, R.anim.fade_in_up);


        circle1.startAnimation(floatAnim);
        circle2.startAnimation(floatAnim);
        heroTitle.startAnimation(fadeInUp);
        fadeInUp.setStartOffset(200);
        heroSubtitle.startAnimation(fadeInUp);
        fadeInUp.setStartOffset(400);
        heroImage.startAnimation(fadeInUp);


        btnExploreFeatures.setOnClickListener(v -> {
            String url = "https://www.dailyhabits.xyz/habit-tracker-app/habithub";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(android.net.Uri.parse(url));
            startActivity(intent);
        });


        btnGetStarted.setOnClickListener(v -> {
            Intent intent = new Intent(StartActivity.this, MidActivity.class);
            startActivity(intent);
        });


        updateStreak(LocalDate.now());
        showDailyQuote();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateStreak(LocalDate currentDate) {
        SharedPreferences prefs = getSharedPreferences("HabitPrefs", MODE_PRIVATE);
        String lastDate = prefs.getString("lastDate", "");
        int streak = prefs.getInt("streak", 0);

        LocalDate today = currentDate;
        LocalDate yesterday = today.minusDays(1);

        int updatedStreak;
        if (lastDate.equals(yesterday.toString())) {
            updatedStreak = streak + 1;
        } else if (lastDate.equals(today.toString())) {
            updatedStreak = streak;
        } else {
            updatedStreak = 1;
        }

        if (streakText != null) {
            streakText.setText("🔥 Streak: " + updatedStreak + " days");
        }

        prefs.edit()
                .putString("lastDate", today.toString())
                .putInt("streak", updatedStreak)
                .apply();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showDailyQuote() {
        if (quoteText != null) {
            String today = LocalDate.now().toString();
            int index = Math.abs(today.hashCode()) % dailyQuotes.size();
            quoteText.setText(dailyQuotes.get(index));
        }
    }
}
