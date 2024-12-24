plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.serialize)
    id("kotlin-parcelize")
}

android {
    namespace = "com.themoviedbdemo"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.themoviedbdemo"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "Token", "\"eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyNmQ0N2U4ZjAyMTU4ZmYyYTMwMzM2OWYyYWNhMGRhNyIsIm5iZiI6MTczNDk0NDg4MC4yNzUsInN1YiI6IjY3NjkyODcwNDZhNWE0Mzg3OTBiMGExYyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.B56yXCqBwMvEQR6lshY0hcvGG2-TuNkrU5B7tXhVnVY\"")

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    //GSON Conversion
    implementation(libs.convetor.gson)
    //API Interceptor
    implementation(libs.retrofit)
    implementation(libs.logging.interceptor)
    implementation(libs.convertgsonkotlin)
    //Glide
    implementation (libs.glide)
    //Pagination
    implementation (libs.paging)
}