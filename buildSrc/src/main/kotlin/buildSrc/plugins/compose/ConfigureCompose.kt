package buildSrc.plugins.compose

import buildSrc.extensions.androidTest
import buildSrc.extensions.androidTestPlatform
import buildSrc.extensions.baseExtension
import buildSrc.extensions.debug
import buildSrc.extensions.implementation
import buildSrc.extensions.implementationPlatform
import buildSrc.extensions.library
import buildSrc.extensions.libraryExtension
import buildSrc.extensions.versionCatalog
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler

internal fun Project.configureCompose() = baseExtension().run {

    composeOptions {
        val compilerVersion =
            versionCatalog.findVersion("androidX-compose-compiler").get()

        kotlinCompilerExtensionVersion = compilerVersion.toString()

    }

    libraryExtension().run {
        buildFeatures {
            compose = true
        }
    }

    dependencies.installCompose(
        project = this@configureCompose,
    )
}

private fun DependencyHandler.installCompose(
    project: Project
) {

    implementationPlatform(project.library("androidX-compose-bom"))
    implementation(project.library("androidX-compose-ui"))
    implementation(project.library("androidX-compose-graphics"))
    implementation(project.library("androidX-compose-tooling-preview"))
    implementation(project.library("androidX-compose-material3"))

    // TODO -> Find out why debug and androidTest are not working

//    debug(project.library("androidX-compose-debug-tooling"))
//    debug(project.library("androidX-compose-debug-manifest"))

//    androidTest(project.library("androidX-compose-test-junit"))
//    androidTestPlatform(project.library("androidX-compose-bom"))
}