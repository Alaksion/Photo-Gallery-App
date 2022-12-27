package extensions

import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.getByType

internal val Project.versionCatalog: VersionCatalog
    get() {
        return extensions.getByType<VersionCatalogsExtension>().named("libs")
    }

internal fun Project.library(alias: String): Provider<MinimalExternalModuleDependency> {
    return versionCatalog.findLibrary(alias).get()
}

fun Project.baseExtension() = extensions.getByType<BaseExtension>()

fun Project.libraryExtension() = extensions.getByType<LibraryExtension>()
