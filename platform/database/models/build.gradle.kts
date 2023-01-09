plugins {
    id("com.mvisample.library")
    id("com.mvisample.hilt")
    id("com.google.devtools.ksp")
}

android {

    namespace = "platform.database.models"

}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}

dependencies {
    implementation(projects.platform.error)
    implementation(projects.platform.logs)
    implementation(projects.platform.test.fixtures)

    implementation(libs.androidX.core.ktx)
    implementation(libs.coroutines.core)
    testImplementation(libs.coroutines.test)

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    testImplementation(libs.junit)
    testImplementation(libs.truth)
}