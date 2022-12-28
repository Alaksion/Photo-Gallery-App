package buildSrc.configurations

import buildSrc.extensions.baseExtension
import buildSrc.extensions.implementation
import buildSrc.extensions.kapt
import buildSrc.extensions.library
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler

internal fun Project.configureHilt() = baseExtension().run {
    plugins.apply("kotlin-kapt")
    plugins.apply("com.google.dagger.hilt.android")

    dependencies.installHilt(this@configureHilt)
}

private fun DependencyHandler.installHilt(
    project: Project
) {
    implementation(project.library("hilt-core"))
    kapt(project.library("hilt-compiler"))
}