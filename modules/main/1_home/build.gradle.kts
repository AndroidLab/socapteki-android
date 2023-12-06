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
    namespace = "ru.apteka.home"
}

dependencies {
    implementation(project(":components"))
    implementation(project(":modules:main:main_common"))
    implementation(project(":modules:features:personal_data:api"))
    implementation(project(":modules:features:choosing_city:api"))
    implementation(project(":modules:features:pharmacies_map:api"))
    implementation(project(":modules:features:product_card:api"))
    implementation(project(":modules:features:our_partners:api"))
    implementation(project(":modules:features:favorites:api"))

    implementation(libs.bundles.navigationDeps)
    implementation(libs.bundles.lifecycleDeps)
}