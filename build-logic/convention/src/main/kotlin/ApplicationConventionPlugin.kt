import com.android.build.api.dsl.ApplicationExtension
import ru.apteka.social.apps.configureModule
import ru.google.samples.apps.nowinandroid.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure


/**
 * Представляет плагин соглашение к модулю приложения.
 */
class ApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("android.application").get().get().pluginId)
            }
            extensions.configure<ApplicationExtension> {
                configureModule(this)
            }
        }
    }
}