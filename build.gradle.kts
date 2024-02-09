import java.util.Properties

// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.kotlinKsp) apply false
    alias(libs.plugins.daggerHiltAndroid) apply false
    alias(libs.plugins.androidxNavigationSafeargs) apply false
    alias(libs.plugins.googleServices) apply false
    alias(libs.plugins.crashlytics) apply false
    alias(libs.plugins.orgSonarqube) apply true
}

val localProperties = Properties().apply {
    load(project.rootProject.file("local.properties").inputStream())
}

subprojects {
    sonar {
        properties {
            property("sonar.sources", "src")
        }
    }
}

sonar {
    properties {
        property("sonar.projectName", "socapteka")
        property("sonar.projectKey", "socapteka")
        property("sonar.host.url", "http://localhost:9000")
        property("sonar.token", requireNotNull(localProperties["sonarToken"]))
    }
}

true // Needed to make the Suppress annotation work for the plugins block
