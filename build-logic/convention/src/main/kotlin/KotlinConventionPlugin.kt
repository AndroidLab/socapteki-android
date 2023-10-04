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
                apply(libs.findPlugin("kotlin.android").get().get().pluginId)
                apply(libs.findPlugin("kotlin.kapt").get().get().pluginId)
                apply(libs.findPlugin("kotlin.ksp").get().get().pluginId)
                apply(libs.findPlugin("kotlin.parcelize").get().get().pluginId)
            }
            dependencies {
                "implementation"(libs.findLibrary("kotlin.stdlib").get())
                "implementation"(libs.findLibrary("kotlinx.coroutines.android").get())
            }
        }
    }
}
