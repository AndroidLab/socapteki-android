@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.libraryConventionPlugin.get().pluginId)
    id(libs.plugins.kotlinConventionPlugin.get().pluginId)
    id(libs.plugins.hiltConventionPlugin.get().pluginId)
    id(libs.plugins.navigationConventionPlugin.get().pluginId)
    id(libs.plugins.retrofitConventionPlugin.get().pluginId)
    id(libs.plugins.roomConventionPlugin.get().pluginId)
    id(libs.plugins.glideConventionPlugin.get().pluginId)
}

android {
    namespace = "ru.apteka.main"
}

dependencies {
    implementation(project(":components"))
    implementation(project(":modules:main:1_home"))
    implementation(project(":modules:main:2_catalog"))
    implementation(project(":modules:main:3_stocks"))
    implementation(project(":modules:main:4_basket"))
    implementation(project(":modules:main:5_menu:_menu"))
    implementation(project(":modules:features:pharmacies_map:api"))
    implementation(project(":modules:features:choosing_city:api"))
    implementation(project(":modules:features:orders:api"))

    implementation(libs.andRatingBar)
}