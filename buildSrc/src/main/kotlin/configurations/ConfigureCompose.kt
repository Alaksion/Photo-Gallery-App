package configurations

import extensions.androidTest
import extensions.baseExtension
import extensions.debug
import extensions.implementation
import extensions.library
import extensions.libraryExtension
import extensions.versionCatalog
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureCompose() = baseExtension().run {

    composeOptions {
        val compilerVersion =
            versionCatalog.findVersion("androidX-compose-compiler").get().preferredVersion

        kotlinCompilerExtensionVersion = compilerVersion

    }

    libraryExtension().run {
        buildFeatures {
            compose = true
        }
    }

    dependencies { dependencies.installCompose(this@configureCompose) }
}

private fun DependencyHandler.installCompose(project: Project) {

    // TODO -> Find a way to add Compose BOM to this setup

    implementation(project.library("androidX-compose-ui"))
    implementation(project.library("androidX-compose-graphics"))
    implementation(project.library("androidX-compose-tooling-preview"))
    implementation(project.library("androidX-compose-material3"))

    debug("androidX-compose-debug-tooling")
    debug("androidX-compose-debug-manifest")

    androidTest("androidX-compose-test-junit")
}