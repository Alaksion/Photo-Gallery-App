package buildSrc.plugins.library

import buildSrc.BuildConstants
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

class AndroidLibraryPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.plugins.apply("com.android.library")
        target.plugins.apply("org.jetbrains.kotlin.android")

        target.extensions.configure<ApplicationExtension> {
            configureKotlinAndroid(this)
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

            buildTypes {
                getByName("release") {
                    isMinifyEnabled = false
                }
            }

            kotlinOptions {
                jvmTarget = BuildConstants.JVM_TARGET
            }

        }

    }

    fun CommonExtension<*, *, *, *>.kotlinOptions(block: KotlinJvmOptions.() -> Unit) {
        (this as ExtensionAware).extensions.configure("kotlinOptions", block)
    }

}