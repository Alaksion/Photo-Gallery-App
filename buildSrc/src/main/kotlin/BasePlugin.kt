import configurations.configureCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property

interface BasePluginConfiguration {
    val useCompose: Property<Boolean>
    val useHilt: Property<Boolean>
}

class BasePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        val configuration =
            target.extensions.create("configuration", BasePluginConfiguration::class.java)
        applyBasePluginDefaultConfig(configuration)

        if (configuration.useCompose.get()) target.configureCompose()

    }

    private fun applyBasePluginDefaultConfig(
        configuration: BasePluginConfiguration
    ) {
        configuration.useCompose.convention(false)
        configuration.useHilt.convention(false)
    }


}