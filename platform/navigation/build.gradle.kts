plugins {
    id("com.mvisample.library")
}

android {

    namespace = "platform.navigation"
}

dependencies {

    implementation(libs.androidX.core.ktx)

    implementation(libs.bundles.voyager)
}