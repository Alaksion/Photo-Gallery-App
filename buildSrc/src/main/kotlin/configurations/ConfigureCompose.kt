package configurations

import extensions.baseExtension
import extensions.libraryExtension
import extensions.versionCatalog
import org.gradle.api.Project

internal fun Project.configureCompose() = baseExtension().run {

    composeOptions {
        kotlinCompilerExtensionVersion =
            versionCatalog.findVersion("androidX-compose-compiler").get().preferredVersion
    }

    libraryExtension().run {
        buildFeatures {
            compose = true
        }
    }

}