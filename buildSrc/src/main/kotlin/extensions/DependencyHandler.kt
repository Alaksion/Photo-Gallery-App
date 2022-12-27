package extensions

import org.gradle.api.artifacts.dsl.DependencyHandler

internal enum class DependencyType(val label: String) {
    Implementation("implementation"),
    Debug("debugImplementation");
}

private fun DependencyHandler.addDependency(
    notation: Any,
    type: DependencyType
) {
    add(type.label, notation.toString())
}

internal fun DependencyHandler.implementation(
    notation: Any
) = addDependency(notation, DependencyType.Implementation)

internal fun DependencyHandler.debug(
    notation: Any
) = addDependency(notation, DependencyType.Debug)