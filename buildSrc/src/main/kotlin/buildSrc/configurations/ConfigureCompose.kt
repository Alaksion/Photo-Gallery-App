package buildSrc.configurations

import buildSrc.extensions.androidTest
import buildSrc.extensions.baseExtension
import buildSrc.extensions.debug
import buildSrc.extensions.implementation
import buildSrc.extensions.library
import buildSrc.extensions.libraryExtension
import buildSrc.extensions.versionCatalog
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureCompose() = baseExtension().run {

    composeOptions {
//        val compilerVersion =
//            versionCatalog.findVersion("androidX-compose-compiler").get().preferredVersion

        kotlinCompilerExtensionVersion = "1.4.0-alpha02"

    }

    libraryExtension().run {
        buildFeatures {
            compose = true
        }
    }

//    dependencies { dependencies.installCompose(this@configureCompose) }
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