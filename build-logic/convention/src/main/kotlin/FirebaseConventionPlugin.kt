import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import ru.google.samples.apps.nowinandroid.libs

/**
 * Представляет плагин соглашение к Firebase.
 */
class FirebaseConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("crashlytics").get().get().pluginId)
            }

            dependencies {
                "implementation"(libs.findLibrary("firebaseCrashlytics").get())
                "implementation"(libs.findLibrary("firebaseAnalytics").get())
            }
        }
    }
}
