import ru.google.samples.apps.nowinandroid.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Представляет плагин соглашение к HILT.
 */
class HiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("daggerHiltAndroid").get().get().pluginId)
                apply(libs.findPlugin("kotlinKapt").get().get().pluginId)
            }

            dependencies {
                "implementation"(libs.findLibrary("hiltAndroid").get())
                "implementation"(libs.findLibrary("hiltNavigationFragment").get())
                "kapt"(libs.findLibrary("hiltAndroidCompiler").get())
                "kapt"(libs.findLibrary("daggerAndroidProcessor").get())
            }
        }
    }
}
