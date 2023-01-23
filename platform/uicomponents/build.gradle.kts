plugins {
    id("com.mvisample.library")
    id("com.mvisample.compose")
}

android {

    namespace = "platform.uicompoments"

}

dependencies {

    implementation(projects.platform.error)
    implementation(projects.platform.logs)

    implementation(libs.androidX.core.ktx)
    implementation(libs.bundles.kotlinState)
    implementation(libs.androidX.lifecycle.runtime)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidX.test.espresso)
    androidTestImplementation(libs.androidX.test.junit)
}