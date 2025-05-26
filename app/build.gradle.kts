plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.habithub"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.habithub"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    // Optional: Helps Gradle enforce correct library versions
    configurations.all {
        resolutionStrategy {
            // Force all core library versions to avoid mismatches
            force("androidx.appcompat:appcompat:1.6.1")
            force("androidx.activity:activity:1.7.2")
            force("androidx.constraintlayout:constraintlayout:2.1.4")
            force("com.google.android.material:material:1.11.0")
        }
    }
}

dependencies {
    // Core Android libraries (use explicit versions to avoid conflicts)
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.activity:activity:1.7.2")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("com.google.android.material:material:1.8.0")
    // Material Components
    implementation("com.google.android.material:material:1.11.0")
    implementation ("com.google.android.material:material:1.10.0") // or latest

    // MPAndroidChart for graphing
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")

    // Testing libraries
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
