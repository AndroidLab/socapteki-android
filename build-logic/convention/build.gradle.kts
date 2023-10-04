import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "ru.apteka.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradle)
    compileOnly(libs.kotlin.gradle)
    compileOnly(libs.ksp.gradle)
}

gradlePlugin {
    plugins {
        register(libs.plugins.apteka.application.convention.plugin.get().pluginId) {
            id = libs.plugins.apteka.application.convention.plugin.get().pluginId
            implementationClass = "ApplicationConventionPlugin"
        }
        register(libs.plugins.apteka.library.convention.plugin.get().pluginId) {
            id = libs.plugins.apteka.library.convention.plugin.get().pluginId
            implementationClass = "LibraryConventionPlugin"
        }
        register(libs.plugins.apteka.hilt.convention.plugin.get().pluginId) {
            id = libs.plugins.apteka.hilt.convention.plugin.get().pluginId
            implementationClass = "HiltConventionPlugin"
        }
        register(libs.plugins.apteka.kotlin.convention.plugin.get().pluginId) {
            id = libs.plugins.apteka.kotlin.convention.plugin.get().pluginId
            implementationClass = "KotlinConventionPlugin"
        }
        register(libs.plugins.apteka.navigation.convention.plugin.get().pluginId) {
            id = libs.plugins.apteka.navigation.convention.plugin.get().pluginId
            implementationClass = "NavigationConventionPlugin"
        }
        register(libs.plugins.apteka.retrofit.convention.plugin.get().pluginId) {
            id = libs.plugins.apteka.retrofit.convention.plugin.get().pluginId
            implementationClass = "RetrofitConventionPlugin"
        }
        register(libs.plugins.apteka.room.convention.plugin.get().pluginId) {
            id = libs.plugins.apteka.room.convention.plugin.get().pluginId
            implementationClass = "RoomConventionPlugin"
        }
        register(libs.plugins.apteka.glide.convention.plugin.get().pluginId) {
            id = libs.plugins.apteka.glide.convention.plugin.get().pluginId
            implementationClass = "RoomConventionPlugin"
        }
    }
}
