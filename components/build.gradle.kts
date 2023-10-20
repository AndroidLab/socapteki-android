@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.apteka.library.convention.plugin.get().pluginId)
    id(libs.plugins.apteka.kotlin.convention.plugin.get().pluginId)
    id(libs.plugins.apteka.hilt.convention.plugin.get().pluginId)
    id(libs.plugins.apteka.navigation.convention.plugin.get().pluginId)
    id(libs.plugins.apteka.retrofit.convention.plugin.get().pluginId)
    id(libs.plugins.apteka.glide.convention.plugin.get().pluginId)
}

android {
    namespace = "ru.apteka.components"
}

dependencies {
    api(libs.decoro)
    api(libs.skeleton)
    api(libs.flexbox)
    implementation(libs.material)
    implementation(libs.gson)
    implementation(libs.swiperefreshlayout)
    implementation(libs.lottie)
}