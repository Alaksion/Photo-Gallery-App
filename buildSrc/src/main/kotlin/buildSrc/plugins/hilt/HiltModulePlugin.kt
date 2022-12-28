package buildSrc.plugins.hilt

import buildSrc.extensions.implementation
import buildSrc.extensions.kapt
import buildSrc.extensions.library
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler

class HiltModulePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {

            with(pluginManager) {
                apply("com.google.dagger.hilt.android")
                apply("kotlin-kapt")
            }

            dependencies.installHilt(this)

        }
    }

    private fun DependencyHandler.installHilt(project: Project) {
        implementation(project.library("hilt-core"))
        kapt(project.library("hilt-compiler"))
    }
}
