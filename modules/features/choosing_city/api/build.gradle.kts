@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.libraryConventionPlugin.get().pluginId)
    id(libs.plugins.kotlinAndroid.get().pluginId)
    id(libs.plugins.kotlinParcelize.get().pluginId)
    id(libs.plugins.navigationConventionPlugin.get().pluginId)
}

android {
    namespace = "ru.apteka.choosing_city_api"
}

dependencies {
    implementation(project(":components"))

    implementation(libs.bundles.navigationDeps)
}