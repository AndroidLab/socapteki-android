@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.libraryConventionPlugin.get().pluginId)
    id(libs.plugins.kotlinConventionPlugin.get().pluginId)
    id(libs.plugins.hiltConventionPlugin.get().pluginId)
    id(libs.plugins.navigationConventionPlugin.get().pluginId)
    id(libs.plugins.retrofitConventionPlugin.get().pluginId)
    id(libs.plugins.glideConventionPlugin.get().pluginId)
    id(libs.plugins.firebaseConventionPlugin.get().pluginId)
}

android {
    namespace = "ru.apteka.components"
}

dependencies {
    api(libs.decoro)
    api(libs.flexbox)
    api(libs.lottie)
    api(libs.expansionpanel)
    api(libs.extraBouncy)
    api(libs.inputLayoutInnerHint)
    implementation(libs.material)
    implementation(libs.gson)
    implementation(libs.swiperefreshlayout)
    implementation(libs.measured)
    implementation(libs.circleimageview)
}