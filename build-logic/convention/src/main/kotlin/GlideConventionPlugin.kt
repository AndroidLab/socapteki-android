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
                apply(libs.findPlugin("kotlin.ksp").get().get().pluginId)
            }
            dependencies {
                "implementation"(libs.findLibrary("glide.glide").get())
                "ksp"(libs.findLibrary("glide.compiler").get())
            }
        }
    }
}
