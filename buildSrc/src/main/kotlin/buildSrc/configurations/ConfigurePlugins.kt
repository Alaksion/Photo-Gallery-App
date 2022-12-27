package buildSrc.configurations

import org.gradle.api.Project

internal fun Project.configurePlugins() {
    plugins.apply("com.android.library")
    plugins.apply("org.jetbrains.kotlin.android")
}