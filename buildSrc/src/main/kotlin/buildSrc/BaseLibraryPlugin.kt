package buildSrc


import buildSrc.configurations.configureAndroid
import buildSrc.configurations.configureCompose
import buildSrc.configurations.configurePlugins
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.create

interface BaseLibraryPluginExtension {
    val useCompose: Property<Boolean>
    val useHilt: Property<Boolean>
}

class BaseLibraryPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        // TOD0 -> Configuration is not working yet
        val configuration = target.extensions.create<BaseLibraryPluginExtension>("config")
        configuration.useCompose.convention(false)

        target.configurePlugins()
        target.configureAndroid()
        // TODO -> Make this function conditional to useCompose configuration
        target.configureCompose()

    }

}