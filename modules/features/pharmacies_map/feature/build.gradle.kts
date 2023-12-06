@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.libraryConventionPlugin.get().pluginId)
    id(libs.plugins.kotlinAndroid.get().pluginId)
    id(libs.plugins.hiltConventionPlugin.get().pluginId)
    id(libs.plugins.navigationConventionPlugin.get().pluginId)
    id(libs.plugins.retrofitConventionPlugin.get().pluginId)
    id(libs.plugins.roomConventionPlugin.get().pluginId)
    id(libs.plugins.glideConventionPlugin.get().pluginId)
}

android {
    namespace = "ru.apteka.pharmacies_map"
}

dependencies {
    implementation(project(":components"))
    implementation(project(":modules:features:pharmacies_map:api"))
    implementation("com.yandex.android:maps.mobile:4.4.0-lite")

    implementation(libs.bundles.navigationDeps)
    implementation(libs.bundles.lifecycleDeps)
}