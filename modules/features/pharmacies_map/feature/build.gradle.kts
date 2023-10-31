@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.apteka.library.convention.plugin.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.apteka.hilt.convention.plugin.get().pluginId)
    id(libs.plugins.apteka.navigation.convention.plugin.get().pluginId)
    id(libs.plugins.apteka.retrofit.convention.plugin.get().pluginId)
    id(libs.plugins.apteka.room.convention.plugin.get().pluginId)
    id(libs.plugins.apteka.glide.convention.plugin.get().pluginId)
}

android {
    namespace = "ru.apteka.pharmacies_map"
}

dependencies {
    implementation(project(":components"))

    implementation("com.yandex.android:maps.mobile:4.4.0-lite")

    implementation(libs.bundles.navigationDeps)
    implementation(libs.lottie)
}