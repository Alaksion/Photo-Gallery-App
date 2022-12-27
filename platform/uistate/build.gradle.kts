plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {

    namespace = "com.example.mvisample"

    compileSdk = buildSrc.BuildConstants.COMPILE_SDK

    defaultConfig {
        minSdk = buildSrc.BuildConstants.MIN_SDK

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
}

dependencies {

    implementation(projects.platform.uicomponents)

    // coroutines
    implementation(libs.coroutines.core)

    // Compose
    implementation(platform(libs.androidX.compose.bom))
    implementation(libs.bundles.compose.ui)

    implementation(libs.androidX.core.ktx)

    // Lifecycle
    implementation(libs.androidX.lifecycle.runtime)
    implementation(libs.androidX.lifecycle.viewmodel)


    // Tests
    testImplementation(libs.junit)
    testImplementation(libs.turbine)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.truth)

    // Android Tests
    androidTestImplementation(platform(libs.androidX.compose.bom))
    androidTestImplementation(libs.androidX.compose.test.junit)
    androidTestImplementation(libs.androidX.test.espresso)
    androidTestImplementation(libs.androidX.test.junit)

    // Debug
    debugImplementation(libs.bundles.compose.debug)
}