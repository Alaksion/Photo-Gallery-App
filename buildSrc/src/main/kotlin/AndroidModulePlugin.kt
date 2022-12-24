import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property

interface AndroidModulePluginExtension {
    val withCompose: Property<Boolean>
}

public class AndroidModulePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        val configurations = target.extensions.create(
            "android-config",
            AndroidModulePluginExtension::class.java
        )

        if (configurations.withCompose.get()) target.addComposeDependencies()

    }

    private fun Project.addComposeDependencies() {
    }

}
