plugins {
    id("com.mvisample.library")
    id("com.mvisample.compose")
    id("com.mvisample.hilt")
}

android {

    namespace = "features.albums.shared"

}

dependencies {

    implementation(projects.platform.database.models)
    implementation(projects.platform.injection)

    // coroutines
    implementation(libs.coroutines.core)
    implementation(libs.androidX.core.ktx)

    // Tests
    testImplementation(libs.junit)
    testImplementation(libs.turbine)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.truth)

}