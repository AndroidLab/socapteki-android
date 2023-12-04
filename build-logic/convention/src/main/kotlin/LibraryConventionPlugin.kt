import com.android.build.api.dsl.LibraryExtension
import ru.apteka.social.apps.configureModule
import ru.google.samples.apps.nowinandroid.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure


/**
 * Представляет плагин соглашение к модулю приложения.
 */
class LibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("android.library").get().get().pluginId)
            }
            extensions.configure<LibraryExtension> {
                configureModule(this)
            }
        }
    }
}