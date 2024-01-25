package ru.apteka.social.apps

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import ru.google.samples.apps.nowinandroid.libs


/**
 * Конфигурирует модуль приложения.
 */
internal fun Project.configureModule(
    commonExtension: CommonExtension<*, *, *, *, *>
) {
    commonExtension.apply {
        compileSdk = libs.findVersion("compile_sdk").get().requiredVersion.toInt()
        defaultConfig {
            minSdk = libs.findVersion("min_sdk").get().requiredVersion.toInt()
            if (this@apply is ApplicationExtension) {
                defaultConfig.targetSdk =
                    libs.findVersion("target_sdk").get().requiredVersion.toInt()
            }
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        when (this@apply) {
            is ApplicationExtension -> {
                buildTypes {
                    debug {
                        isMinifyEnabled = libs.findVersion("application_minify_debug_enabled")
                            .get().requiredVersion.toBoolean()
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                    }
                    release {
                        isMinifyEnabled = libs.findVersion("application_minify_release_enabled")
                            .get().requiredVersion.toBoolean()
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                    }
                }
            }

            is LibraryExtension -> {
                buildTypes {
                    debug {
                        isMinifyEnabled = libs.findVersion("library_minify_debug_enabled")
                            .get().requiredVersion.toBoolean()
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                    }
                    release {
                        isMinifyEnabled = libs.findVersion("library_minify_release_enabled")
                            .get().requiredVersion.toBoolean()
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                    }
                }
            }
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }

        buildFeatures {
            dataBinding {
                enable = true
            }
        }

        lint {
            disable.addAll(
                listOf(
                    "NullSafeMutableLiveData",
                    "MissingClass",
                    "NewApi"
                )
            )
        }

        tasks.withType<KotlinCompile>().configureEach {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_17.toString()
                val warningsAsErrors: String? by project
                allWarningsAsErrors = warningsAsErrors.toBoolean()
                freeCompilerArgs = freeCompilerArgs + listOf(
                    "-opt-in=kotlin.RequiresOptIn",
                    // Enable experimental coroutines APIs, including Flow
                    "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                    "-opt-in=kotlinx.coroutines.FlowPreview",
                )
            }
        }
    }
}