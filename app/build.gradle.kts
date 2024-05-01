plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
}

android {
    namespace = "com.dmitLugg.weatherapp"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.dmitLugg.weatherapp"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0"
    }
    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation("androidx.activity:activity-compose:1.6.1")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.28.0")
    implementation("androidx.navigation:navigation-compose:2.5.3")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    implementation("androidx.activity:activity-ktx:1.6.1")

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")
    // dagger
    implementation("com.google.dagger:dagger:2.42")
    kapt("com.google.dagger:dagger-compiler:2.42")

    // retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Pager
    implementation("com.google.accompanist:accompanist-pager:0.29.0-alpha")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.29.0-alpha")

    val appcompatVersion = "1.6.1"

    implementation("androidx.appcompat:appcompat:$appcompatVersion")

    val composeVersion  = "1.2.1"

    // icons
    implementation("androidx.compose.material:material-icons-extended:$composeVersion")

    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation("androidx.compose.material3:material3:1.1.0-alpha04")
    testImplementation("junit:junit:4.13.2")
//    androidTestImplementation("androidx.test.ext:junit:1.1.4")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0")
//    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
//    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeVersion")
}