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
    namespace = "ru.apteka.menu"
}

dependencies {
    implementation(project(":components"))
    implementation(project(":modules:main:5_menu:notifications"))
    implementation(project(":modules:main:5_menu:about_company"))
    implementation(project(":modules:main:5_menu:work_with_us"))
    implementation(project(":modules:main:5_menu:profile"))
    implementation(project(":modules:main:5_menu:contacts"))
    implementation(project(":modules:main:5_menu:licenses"))
    implementation(project(":modules:main:5_menu:brands"))
    implementation(project(":modules:main:5_menu:charity"))
    implementation(project(":modules:main:5_menu:referral_program"))
    implementation(project(":modules:main:5_menu:symptoms_diseases"))
    implementation(project(":modules:main:5_menu:customers"))

    implementation(libs.bundles.navigationDeps)
    implementation(libs.bundles.lifecycleDeps)
}