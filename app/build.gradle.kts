plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.mvisample"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.mvisample"
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
        getByName("release") {
            isMinifyEnabled = false
            setProguardFiles(listOf(getDefaultProguardFile("proguard-android-optimize.txt")))
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
        kotlinCompilerExtensionVersion = "1.3.2"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(projects.platform.uistate)

    implementation(platform(libs.androidX.compose.bom))
    implementation(libs.androidX.core.ktx)
    implementation(libs.androidX.lifecycle.runtime)
    implementation(libs.androidX.compose.activity)
    implementation(libs.bundles.compose.ui)

    testImplementation(libs.junit)

    androidTestImplementation(platform(libs.androidX.compose.bom))
    androidTestImplementation(libs.androidX.compose.test.junit)
    androidTestImplementation(libs.androidX.test.espresso)
    androidTestImplementation(libs.androidX.test.junit)

    debugImplementation(libs.bundles.compose.debug)
}