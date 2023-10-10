@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.apteka.library.convention.plugin.get().pluginId)
    id(libs.plugins.apteka.kotlin.convention.plugin.get().pluginId)
    id(libs.plugins.apteka.hilt.convention.plugin.get().pluginId)
    id(libs.plugins.apteka.glide.convention.plugin.get().pluginId)
}

android {
    namespace = "ru.apteka.common"
}

dependencies {
    implementation(project(":resources"))

    //implementation(libs.glide.glide)
    implementation(libs.appcompat)
    implementation(libs.bundles.navigationDeps)
    implementation(libs.swiperefreshlayout)
}