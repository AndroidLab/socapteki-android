@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.libraryConventionPlugin.get().pluginId)
    id(libs.plugins.kotlinAndroid.get().pluginId)
    id(libs.plugins.kotlinParcelize.get().pluginId)
    id(libs.plugins.hiltConventionPlugin.get().pluginId)
    id(libs.plugins.navigationConventionPlugin.get().pluginId)
    id(libs.plugins.retrofitConventionPlugin.get().pluginId)
    id(libs.plugins.glideConventionPlugin.get().pluginId)
    id(libs.plugins.firebaseConventionPlugin.get().pluginId)
}

android {
    namespace = "ru.apteka.notifications"
}

dependencies {
    implementation(project(":components"))

    implementation(libs.bundles.navigationDeps)
    implementation(libs.circleimageview)
}
