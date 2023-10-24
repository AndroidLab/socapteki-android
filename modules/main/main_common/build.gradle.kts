@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.apteka.library.convention.plugin.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
	id(libs.plugins.kotlin.parcelize.get().pluginId)
    id(libs.plugins.apteka.hilt.convention.plugin.get().pluginId)
}

android {
    namespace = "ru.apteka.main_common"
}

dependencies {
    implementation(project(":components"))

    implementation(libs.bundles.navigationDeps)
    implementation(libs.bundles.lifecycleDeps)
}