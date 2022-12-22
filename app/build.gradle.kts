plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.mvisample"
    compileSdk = BuildConstants.COMPILE_SDK

    defaultConfig {
        applicationId = "com.example.mvisample"
        minSdk = BuildConstants.MIN_SDK
        targetSdk = BuildConstants.TARGET_SDK
        versionCode = BuildConstants.VERSION_CODE
        versionName = BuildConstants.VERSION_NAME

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
        sourceCompatibility = BuildConstants.JAVA_VERSION
        targetCompatibility = BuildConstants.JAVA_VERSION
    }
    kotlinOptions {
        jvmTarget = BuildConstants.JVM_TARGET
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

    implementation(projects.platform.uistate)

    implementation(platform(libs.androidX.compose.bom))
    implementation(libs.androidX.core.ktx)
    implementation(libs.androidX.lifecycle.runtime)
    implementation(libs.androidX.compose.activity)
    implementation(libs.bundles.compose.ui)
    implementation(libs.coil)

    testImplementation(libs.junit)

    androidTestImplementation(platform(libs.androidX.compose.bom))
    androidTestImplementation(libs.androidX.compose.test.junit)
    androidTestImplementation(libs.androidX.test.espresso)
    androidTestImplementation(libs.androidX.test.junit)

    debugImplementation(libs.bundles.compose.debug)
}