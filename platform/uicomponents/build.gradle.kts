plugins {
    id("com.mvisample.library")
    id("com.mvisample.compose")
}

android {

    namespace = "platform.uicompoments"

}

dependencies {

    implementation(projects.platform.error)

    implementation(libs.androidX.core.ktx)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidX.test.espresso)
    androidTestImplementation(libs.androidX.test.junit)
}