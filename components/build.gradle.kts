@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.apteka.library.convention.plugin.get().pluginId)
    id(libs.plugins.apteka.kotlin.convention.plugin.get().pluginId)
    id(libs.plugins.apteka.hilt.convention.plugin.get().pluginId)
    id(libs.plugins.apteka.navigation.convention.plugin.get().pluginId)
}

android {
    namespace = "ru.apteka.components"
}

dependencies {
    implementation(project(":common"))
    implementation(project(":resources"))

    api(libs.decoro)
    api(libs.skeleton)
    implementation(libs.material)
    implementation(libs.gson)
}