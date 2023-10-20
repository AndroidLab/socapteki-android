@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.apteka.library.convention.plugin.get().pluginId)
    id(libs.plugins.apteka.kotlin.convention.plugin.get().pluginId)
    id(libs.plugins.apteka.hilt.convention.plugin.get().pluginId)
    id(libs.plugins.apteka.navigation.convention.plugin.get().pluginId)
    id(libs.plugins.apteka.retrofit.convention.plugin.get().pluginId)
    id(libs.plugins.apteka.room.convention.plugin.get().pluginId)
    id(libs.plugins.apteka.glide.convention.plugin.get().pluginId)
}

android {
    namespace = "ru.apteka.main"
}

dependencies {
    implementation(project(":components"))
    implementation(project(":modules:main:1_home"))
    implementation(project(":modules:main:2_catalog"))
    implementation(project(":modules:main:3_orders"))
    implementation(project(":modules:main:4_favorites"))
    implementation(project(":modules:main:5_basket"))
}