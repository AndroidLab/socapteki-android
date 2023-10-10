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
include(":main:1_home")
include(":main:2_catalog")
include(":main:3_orders")
include(":main:4_favorites")
include(":main:5_basket")
