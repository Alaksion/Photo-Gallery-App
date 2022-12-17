import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektPlugin

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply (false)
    alias(libs.plugins.android.library) apply (false)
    alias(libs.plugins.kotlin.jvm) apply (false)
    alias(libs.plugins.kotlin.android) apply (false)
    alias(libs.plugins.detekt) apply (false)
}

subprojects {
    apply<DetektPlugin>()

    tasks {
        withType<Detekt> {
            parallel = true
            config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
            buildUponDefaultConfig = false
        }
    }
}