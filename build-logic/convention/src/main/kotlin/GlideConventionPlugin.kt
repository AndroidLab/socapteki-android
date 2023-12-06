import ru.google.samples.apps.nowinandroid.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

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
                //"ksp"(libs.findLibrary("glideCompiler").get())
            }
        }
    }
}
