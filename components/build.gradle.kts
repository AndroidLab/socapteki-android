@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.apteka.library.convention.plugin.get().pluginId)
    id(libs.plugins.apteka.kotlin.convention.plugin.get().pluginId)
}

android {
    namespace = "ru.apteka.components"
}

dependencies {
    implementation(project(":common"))

    api(libs.decoro)
    implementation(libs.material)
}