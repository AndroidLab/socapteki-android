@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.libraryConventionPlugin.get().pluginId)
    id(libs.plugins.kotlinAndroid.get().pluginId)
	id(libs.plugins.kotlinParcelize.get().pluginId)
    id(libs.plugins.hiltConventionPlugin.get().pluginId)
    id(libs.plugins.navigationConventionPlugin.get().pluginId)
    id(libs.plugins.retrofitConventionPlugin.get().pluginId)
    id(libs.plugins.roomConventionPlugin.get().pluginId)
    id(libs.plugins.glideConventionPlugin.get().pluginId)
}

android {
    namespace = "ru.apteka.making_order"
}

dependencies {
    implementation(project(":components"))
    implementation(project(":modules:features:making_order:api"))
    implementation(project(":modules:features:choosing_city:api"))
    implementation(project(":modules:features:pharmacies_map:api"))
    implementation(project(":modules:features:personal_data:api"))

    implementation(libs.bundles.navigationDeps)
    implementation(libs.bundles.lifecycleDeps)
    implementation(libs.expansionpanel)
}