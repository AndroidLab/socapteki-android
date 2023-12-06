import ru.google.samples.apps.nowinandroid.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Представляет плагин соглашение к Kotlin.
 */
class KotlinConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("kotlinAndroid").get().get().pluginId)
                apply(libs.findPlugin("kotlinKapt").get().get().pluginId)
                apply(libs.findPlugin("kotlinKsp").get().get().pluginId)
                apply(libs.findPlugin("kotlinParcelize").get().get().pluginId)
            }
            dependencies {
                "implementation"(libs.findLibrary("kotlinStdlib").get())
                "implementation"(libs.findLibrary("kotlinxCoroutinesAndroid").get())
            }
        }
    }
}
