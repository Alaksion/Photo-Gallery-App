plugins {
    id("com.mvisample.library")
    id("com.mvisample.hilt")
}

android {

    namespace = "platform.injection"

}

dependencies {

    implementation(libs.androidX.core.ktx)

    implementation(libs.coroutines.core)
}