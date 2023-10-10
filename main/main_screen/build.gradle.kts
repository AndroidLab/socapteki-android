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
    implementation(project(":common"))
    implementation(project(":components"))
    implementation(project(":resources"))
    implementation(project(":main:screen_1"))
    implementation(project(":main:screen_2"))
    implementation(project(":main:screen_3"))
    implementation(project(":main:screen_4"))
    implementation(project(":main:screen_5"))
}