@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.libraryConventionPlugin.get().pluginId)
    id(libs.plugins.kotlinAndroid.get().pluginId)
    id(libs.plugins.kotlinParcelize.get().pluginId)
    id(libs.plugins.hiltConventionPlugin.get().pluginId)
    id(libs.plugins.navigationConventionPlugin.get().pluginId)
    id(libs.plugins.retrofitConventionPlugin.get().pluginId)
    id(libs.plugins.glideConventionPlugin.get().pluginId)
    //id(libs.plugins.firebaseConventionPlugin.get().pluginId)
}

android {
    namespace = "ru.apteka.home"
}

dependencies {
    implementation(project(":components"))
    implementation(project(":modules:features:listing"))
    implementation(project(":modules:features:choosing_city:api"))
    implementation(project(":modules:features:pharmacies_map:api"))
    implementation(project(":modules:features:product_card:api"))
    implementation(project(":modules:features:favorites:api"))
    implementation(project(":modules:features:order_details:api"))

    implementation(libs.bundles.navigationDeps)
    implementation(libs.bundles.lifecycleDeps)
}