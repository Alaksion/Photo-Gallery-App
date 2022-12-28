plugins {
    id("com.mvisample.library")
    id("com.mvisample.compose")
}

android {

    namespace = "platform.uistate"

}

dependencies {

    implementation(projects.platform.uicomponents)

    // coroutines
    implementation(libs.coroutines.core)

    implementation(libs.androidX.core.ktx)

    // Lifecycle
    implementation(libs.androidX.lifecycle.runtime)
    implementation(libs.androidX.lifecycle.viewmodel)


    // Tests
    testImplementation(libs.junit)
    testImplementation(libs.turbine)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.truth)


    androidTestImplementation(libs.androidX.test.espresso)
    androidTestImplementation(libs.androidX.test.junit)

}