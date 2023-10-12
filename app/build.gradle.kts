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
    implementation(project(":modules:main:main_screen"))
    implementation(project(":modules:main:1_home"))
    implementation(project(":modules:main:2_catalog"))
    implementation(project(":modules:main:3_orders"))
    implementation(project(":modules:main:4_favorites"))
    implementation(project(":modules:main:5_basket"))
    implementation(project(":modules:common:choosing_city"))
    implementation(project(":modules:common:about_company"))
    implementation(project(":modules:common:brands"))
    implementation(project(":modules:common:pharmacies_map"))
    implementation(project(":modules:common:advantages"))
    implementation(project(":modules:common:partners"))

    //Google & Android
    implementation(libs.core.ktx)
    implementation(libs.constraintlayout)
    implementation(libs.material)
    implementation(libs.gson)
    implementation(libs.coreSplashscreen)
    implementation(libs.bundles.lifecycleDeps)
}