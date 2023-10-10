@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id(libs.plugins.apteka.application.convention.plugin.get().pluginId)
    id(libs.plugins.apteka.kotlin.convention.plugin.get().pluginId)
    id(libs.plugins.apteka.hilt.convention.plugin.get().pluginId)
    id(libs.plugins.apteka.navigation.convention.plugin.get().pluginId)
    id(libs.plugins.apteka.retrofit.convention.plugin.get().pluginId)
}

private val major = 0
private val minor = 1
private val patch = 0

android {
    namespace = "ru.apteka.social"

    defaultConfig {
        applicationId = "ru.apteka.social"
        versionCode = major * 10000 + minor * 100 + patch
        versionName = "${major}.${minor}.${patch}"
    }
}

dependencies {
    implementation(project(":common"))
    implementation(project(":resources"))
    implementation(project(":components"))
    implementation(project(":network"))
    implementation(project(":main:main_screen"))
    implementation(project(":main:1_home"))
    implementation(project(":main:2_catalog"))
    implementation(project(":main:3_orders"))
    implementation(project(":main:4_favorites"))
    implementation(project(":main:5_basket"))

    //Google & Android
    implementation(libs.core.ktx)
    implementation(libs.constraintlayout)
    implementation(libs.material)
    implementation(libs.gson)
    implementation(libs.bundles.lifecycleDeps)
}