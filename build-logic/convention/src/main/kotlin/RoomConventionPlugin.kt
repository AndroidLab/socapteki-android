import ru.google.samples.apps.nowinandroid.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Представляет плагин соглашение к Room.
 */
class RoomConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("kotlinKsp").get().get().pluginId)
            }
            dependencies {
                "implementation"(libs.findLibrary("roomRuntime").get())
                "implementation"(libs.findLibrary("roomKtx").get())
                "ksp"(libs.findLibrary("roomCompiler").get())
            }
        }
    }
}
