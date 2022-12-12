plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {

    namespace = "com.example.mvisample"

    compileSdk = 33

    defaultConfig {
        minSdk = 26
        targetSdk = 33

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
}

dependencies {

    implementation(projects.platform.uistate)

    implementation(platform(libs.androidX.compose.bom))
    implementation(libs.androidX.core.ktx)
    implementation(libs.androidX.lifecycle.runtime)
    implementation(libs.androidX.compose.activity)
    implementation(libs.androidX.compose.ui)
    implementation(libs.androidX.compose.graphics)
    implementation(libs.androidX.compose.tooling.preview)
    implementation(libs.androidX.compose.material3)

    testImplementation(libs.junit)

    androidTestImplementation(platform(libs.androidX.compose.bom))
    androidTestImplementation(libs.androidX.compose.test.junit)
    androidTestImplementation(libs.androidX.test.espresso)
    androidTestImplementation(libs.androidX.test.junit)

    debugImplementation(libs.androidX.compose.debug.tooling)
    debugImplementation(libs.androidX.compose.debug.manifest)
}