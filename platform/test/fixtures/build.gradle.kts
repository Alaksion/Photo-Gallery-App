plugins {
    id("com.mvisample.library")
}

android {

    namespace = "platform.test.fixtures"

}

dependencies {
    implementation(projects.platform.logs)
}