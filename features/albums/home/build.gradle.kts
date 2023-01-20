plugins {
    id("com.mvisample.library")
    id("com.mvisample.compose")
    id("com.mvisample.hilt")
}

android {

    namespace = "features.albums.home"

}

dependencies {

    implementation(projects.platform.uicomponents)
    implementation(projects.platform.database.models)
    implementation(projects.platform.navigation)
    implementation(projects.platform.injection)
    implementation(projects.features.albums.shared)

    implementation(libs.bundles.kotlinState)
    // Navigation
    implementation(libs.bundles.voyager)

    // coroutines
    implementation(libs.coroutines.core)

    implementation(libs.androidX.core.ktx)

    // Lifecycle
    implementation(libs.androidX.lifecycle.runtime)
    implementation(libs.androidX.lifecycle.viewmodel)

    implementation(libs.accompanist.pullRefresh)

    // Tests
    testImplementation(libs.junit)
    testImplementation(libs.turbine)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.truth)

    androidTestImplementation(libs.androidX.test.espresso)
    androidTestImplementation(libs.androidX.test.junit)

}