import ru.google.samples.apps.nowinandroid.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Представляет плагин соглашение к Navigation.
 */
class NavigationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("androidxNavigationSafeargs").get().get().pluginId)
            }
            dependencies {
                "implementation"(libs.findLibrary("navigationFragmentKtx").get())
                "implementation"(libs.findLibrary("navigationUiKtx").get())
            }
        }
    }
}
