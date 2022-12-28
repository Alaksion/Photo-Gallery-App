package buildSrc.plugins.compose

import org.gradle.api.Plugin
import org.gradle.api.Project

class ComposeModulePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.configureCompose()
    }

}