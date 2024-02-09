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
    compileOnly(libs.androidGradle)
    compileOnly(libs.kotlinGradle)
    compileOnly(libs.kspGradle)
}

gradlePlugin {
    plugins {
        register(libs.plugins.applicationConventionPlugin.get().pluginId) {
            id = libs.plugins.applicationConventionPlugin.get().pluginId
            implementationClass = libs.plugins.applicationConventionPlugin.get().pluginId
        }
        register(libs.plugins.libraryConventionPlugin.get().pluginId) {
            id = libs.plugins.libraryConventionPlugin.get().pluginId
            implementationClass = libs.plugins.libraryConventionPlugin.get().pluginId
        }
        register(libs.plugins.hiltConventionPlugin.get().pluginId) {
            id = libs.plugins.hiltConventionPlugin.get().pluginId
            implementationClass = libs.plugins.hiltConventionPlugin.get().pluginId
        }
        register(libs.plugins.kotlinConventionPlugin.get().pluginId) {
            id = libs.plugins.kotlinConventionPlugin.get().pluginId
            implementationClass = libs.plugins.kotlinConventionPlugin.get().pluginId
        }
        register(libs.plugins.navigationConventionPlugin.get().pluginId) {
            id = libs.plugins.navigationConventionPlugin.get().pluginId
            implementationClass = libs.plugins.navigationConventionPlugin.get().pluginId
        }
        register(libs.plugins.retrofitConventionPlugin.get().pluginId) {
            id = libs.plugins.retrofitConventionPlugin.get().pluginId
            implementationClass = libs.plugins.retrofitConventionPlugin.get().pluginId
        }
        register(libs.plugins.roomConventionPlugin.get().pluginId) {
            id = libs.plugins.roomConventionPlugin.get().pluginId
            implementationClass = libs.plugins.roomConventionPlugin.get().pluginId
        }
        register(libs.plugins.glideConventionPlugin.get().pluginId) {
            id = libs.plugins.glideConventionPlugin.get().pluginId
            implementationClass = libs.plugins.glideConventionPlugin.get().pluginId
        }
        register(libs.plugins.googleServicesConventionPlugin.get().pluginId) {
            id = libs.plugins.googleServicesConventionPlugin.get().pluginId
            implementationClass = libs.plugins.googleServicesConventionPlugin.get().pluginId
        }
        register(libs.plugins.firebaseConventionPlugin.get().pluginId) {
            id = libs.plugins.firebaseConventionPlugin.get().pluginId
            implementationClass = libs.plugins.firebaseConventionPlugin.get().pluginId
        }
    }
}
