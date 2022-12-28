package buildSrc.extensions

import org.gradle.api.artifacts.dsl.DependencyHandler

internal enum class DependencyType(val label: String) {
    Implementation("implementation"),
    Debug("debugImplementation"),
    Kapt("kapt"),
    AndroidTest("androidTestImplementation");
}

private fun DependencyHandler.addDependency(
    notation: Any,
    type: DependencyType
) {
    add(
        type.label,
        notation,
    )
}

internal fun DependencyHandler.implementation(
    notation: Any
) = addDependency(notation, DependencyType.Implementation)

internal fun DependencyHandler.kapt(
    notation: Any
) = addDependency(notation, DependencyType.Kapt)