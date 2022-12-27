package buildSrc.configurations

import buildSrc.BuildConstants
import buildSrc.extensions.baseExtension
import buildSrc.extensions.libraryExtension
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

internal fun Project.configureAndroid() = baseExtension().run {

    libraryExtension().run {
        // TODO -> See if this namespace can be shared or if it must be unique for every module
        namespace = "com.example.mvisample"

        compileSdk = BuildConstants.COMPILE_SDK
    }

    defaultConfig {
        minSdk = BuildConstants.MIN_SDK
        targetSdk = BuildConstants.TARGET_SDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    compileOptions {
        sourceCompatibility = BuildConstants.JAVA_VERSION
        targetCompatibility = BuildConstants.JAVA_VERSION
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    tasks.withType(KotlinJvmCompile::class.java) {
        kotlinOptions {
            jvmTarget = BuildConstants.JVM_TARGET
        }
    }

}