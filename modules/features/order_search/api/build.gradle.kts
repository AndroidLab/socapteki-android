@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.apteka.library.convention.plugin.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.kotlin.parcelize.get().pluginId)
    id(libs.plugins.apteka.navigation.convention.plugin.get().pluginId)
}

android {
    namespace = "ru.apteka.order_search_api"
}

dependencies {
    implementation(project(":components"))

    implementation(libs.bundles.navigationDeps)
}