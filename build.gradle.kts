import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektPlugin
import io.gitlab.arturbosch.detekt.report.ReportMergeTask

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

    val reportMerge by tasks.registering(ReportMergeTask::class) {
        output.set(rootProject.layout.buildDirectory.file("reports/detekt/merge.html"))
    }

    tasks.withType<Detekt>() {
        reports {
            parallel = true
            config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
            buildUponDefaultConfig = false
            basePath = projectDir.absolutePath

            reports {
                sarif {
                    required.set(true)
                    outputLocation.set(file("$rootDir/build/report/detekt/detekt.sarif"))
                }
                html {
                    required.set(true)
                    outputLocation.set(file("$rootDir/build/report/detekt/detekt.html"))
                }
            }

            finalizedBy(reportMerge)

            reportMerge.configure {
                input.from(htmlReportFile)
            }
        }
    }

}