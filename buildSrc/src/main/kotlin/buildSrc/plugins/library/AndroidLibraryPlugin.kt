package buildSrc.plugins.library

import buildSrc.BuildConstants
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

class AndroidLibraryPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {

            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
            }
        }
    }

    private fun configureKotlinAndroid(
        extension: CommonExtension<*, *, *, *>
    ) {
        extension.apply {
            compileSdk = BuildConstants.COMPILE_SDK

            defaultConfig {
                minSdk = BuildConstants.MIN_SDK

                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                vectorDrawables {
                    useSupportLibrary = true
                }
            }

            compileOptions {
                sourceCompatibility = BuildConstants.JAVA_VERSION
                targetCompatibility = BuildConstants.JAVA_VERSION
            }

            buildFeatures {
                buildConfig = true
            }

            buildTypes {
                getByName("release") {
                    isMinifyEnabled = false
                    buildConfigField(type = "String", name = "label", "\"release\"")
                }

                getByName("debug") {
                    buildConfigField(type = "String", name = "label", "\"debug\"")
                }
            }

            kotlinOptions {
                jvmTarget = BuildConstants.JVM_TARGET
            }

        }

    }

    private fun CommonExtension<*, *, *, *>.kotlinOptions(block: KotlinJvmOptions.() -> Unit) {
        (this as ExtensionAware).extensions.configure("kotlinOptions", block)
    }
}