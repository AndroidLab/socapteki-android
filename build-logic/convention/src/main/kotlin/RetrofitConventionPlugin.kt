import ru.google.samples.apps.nowinandroid.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Представляет плагин соглашение к Retrofit.
 */
class RetrofitConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                "implementation"(libs.findLibrary("retrofit").get())
                "implementation"(libs.findLibrary("converterGson").get())
                "implementation"(libs.findLibrary("loggingInterceptor").get())
                //"implementation"(libs.findLibrary("converter.scalars").get())
            }
        }
    }
}
