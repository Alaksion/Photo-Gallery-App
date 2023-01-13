import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektPlugin
import io.gitlab.arturbosch.detekt.report.ReportMergeTask

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.detekt) apply (false)
    alias(libs.plugins.ksp) apply (false)
    alias(libs.plugins.hilt) apply (false)
    alias(libs.plugins.gradle.versions)
    alias(libs.plugins.map.secret) apply false
}

subprojects {

    apply<DetektPlugin>()

    val reportMerge by tasks.registering(ReportMergeTask::class) {
        output.set(rootProject.layout.buildDirectory.file("reports/detekt/merge.sarif"))
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
                    outputLocation.set(file("${project.buildDir}/build/report/detekt/detekt.sarif"))
                }
                html {
                    required.set(true)
                    outputLocation.set(file("$rootDir/build/report/detekt/detekt.html"))
                }
            }

            finalizedBy(reportMerge)

            reportMerge.configure {
                input.from(sarifReportFile)
            }
        }
    }
}

// Configuration for ./gradlew dependencyUpdates
tasks.withType<DependencyUpdatesTask>() {
    checkForGradleUpdate = false
    outputFormatter = "json"
    outputDir = "build/dependencyReports"
    reportfileName = "report"
}