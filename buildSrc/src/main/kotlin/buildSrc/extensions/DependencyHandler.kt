package buildSrc.extensions

import org.gradle.api.artifacts.dsl.DependencyHandler

internal enum class DependencyType(val label: String) {
    Implementation("implementation"),
    Debug("debugImplementation"),
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

internal fun DependencyHandler.debug(
    notation: Any
) = addDependency(notation, DependencyType.Debug)

internal fun DependencyHandler.androidTest(
    notation: Any
) = addDependency(notation, DependencyType.AndroidTest)