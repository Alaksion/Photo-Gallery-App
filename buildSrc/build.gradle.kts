plugins {
    `kotlin-dsl`
    `version-catalog`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(gradleApi())
    implementation(libs.customPlugin.kotlinGradle)
    implementation(libs.customPlugin.agp)

    /*
    * JavaPoet 1.13 must be downloaded individually because Gradle is downloading an older version
    * for some reason. This version is required because it's used by Hilt, without it, the build
    *  throws a NoSuchMethod exception..
    * */
    implementation("com.squareup:javapoet:1.13.0")
}

gradlePlugin {
    plugins {
        register("androidLibrary") {
            id = "com.mvisample.library"
            implementationClass = "buildSrc.plugins.library.AndroidLibraryPlugin"
        }
        register("composeLibrary") {
            id = "com.mvisample.compose"
            implementationClass = "buildSrc.plugins.compose.ComposeModulePlugin"
        }
        register("hilt-library") {
            id = "com.mvisample.hilt"
            implementationClass = "buildSrc.plugins.hilt.HiltModulePlugin"
        }
    }
}