import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import ru.google.samples.apps.nowinandroid.libs

/**
 * Представляет плагин соглашение к GoogleServices.
 */
class GoogleServicesConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("googleServices").get().get().pluginId)
            }
            dependencies {
                "implementation"(libs.findLibrary("firebaseMessaging").get())
            }
        }
    }
}
