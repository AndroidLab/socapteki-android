pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "socapteka"
include(":app")
include(":common")
include(":resources")
include(":network")
include(":components")
include(":main:main_screen")
include(":main:screen_1")
include(":main:screen_2")
include(":main:screen_3")
include(":main:screen_4")
include(":main:screen_5")
