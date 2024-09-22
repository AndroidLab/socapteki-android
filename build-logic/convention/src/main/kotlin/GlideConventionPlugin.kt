import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import ru.google.samples.apps.nowinandroid.libs

/**
 * Представляет плагин соглашение к Glide.
 */
class GlideConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("kotlinKsp").get().get().pluginId)
            }
            dependencies {
                "implementation"(libs.findLibrary("glideGlide").get())
                "implementation"(libs.findLibrary("glideOkhttp3").get())
                "ksp"(libs.findLibrary("glideKsp").get())
            }
        }
    }
}
