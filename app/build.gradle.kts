plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.example.mvisample"
    compileSdk = buildSrc.BuildConstants.COMPILE_SDK

    defaultConfig {
        applicationId = "com.example.mvisample"
        minSdk = buildSrc.BuildConstants.MIN_SDK
        targetSdk = buildSrc.BuildConstants.TARGET_SDK
        versionCode = buildSrc.BuildConstants.VERSION_CODE
        versionName = buildSrc.BuildConstants.VERSION_NAME

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
        sourceCompatibility = buildSrc.BuildConstants.JAVA_VERSION
        targetCompatibility = buildSrc.BuildConstants.JAVA_VERSION
    }
    kotlinOptions {
        jvmTarget = buildSrc.BuildConstants.JVM_TARGET
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidX.compose.compiler.get()
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(projects.platform.navigation)
    implementation(projects.platform.uicomponents)
    implementation(projects.features.albums.home)
    implementation(projects.features.albums.create)

    implementation(libs.androidX.core.ktx)
    implementation(libs.androidX.lifecycle.runtime)
    implementation(libs.androidX.compose.activity)
    implementation(libs.bundles.compose.ui)
    implementation(libs.coil)

    // Navigation
    implementation(libs.bundles.voyager)


    implementation(libs.hilt.core)
    kapt(libs.hilt.compiler)

    testImplementation(libs.junit)

    implementation(libs.coroutines.core)
    testImplementation(libs.coroutines.test)

    androidTestImplementation(libs.androidX.compose.test.junit)
    androidTestImplementation(libs.androidX.test.espresso)
    androidTestImplementation(libs.androidX.test.junit)

    debugImplementation(libs.bundles.compose.debug)
}

kapt {
    correctErrorTypes = true
}