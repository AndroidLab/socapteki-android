@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.libraryConventionPlugin.get().pluginId)
    id(libs.plugins.kotlinConventionPlugin.get().pluginId)
    id(libs.plugins.hiltConventionPlugin.get().pluginId)
    id(libs.plugins.navigationConventionPlugin.get().pluginId)
    id(libs.plugins.retrofitConventionPlugin.get().pluginId)
    id(libs.plugins.roomConventionPlugin.get().pluginId)
    id(libs.plugins.glideConventionPlugin.get().pluginId)
}

android {
    namespace = "ru.apteka.feature_screen"
}

dependencies {
    implementation(project(":components"))

    implementation(libs.andRatingBar)
}