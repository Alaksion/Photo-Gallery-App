package buildSrc.plugins.compose

import buildSrc.extensions.DependencyType
import buildSrc.extensions.baseExtension
import buildSrc.extensions.implementation
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
    implementation(project.library("androidX-compose-ui"))
    implementation(project.library("androidX-compose-graphics"))
    implementation(project.library("androidX-compose-tooling-preview"))
    implementation(project.library("androidX-compose-material3"))

    // Manual add because androidTest and debug extensions are not working for unknown reasons
    add(DependencyType.Debug.label, project.library("androidX-compose-debug-tooling"))
    add(DependencyType.Debug.label, project.library("androidX-compose-debug-manifest"))

    add(DependencyType.AndroidTest.label, project.library("androidX-compose-test-junit"))
}