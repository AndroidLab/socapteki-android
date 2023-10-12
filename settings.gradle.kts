import java.net.URI

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
        maven {
            url = URI("https://jitpack.io")
        }
    }
}

rootProject.name = "socapteka"
include(":app")
include(":common")
include(":resources")
include(":network")
include(":components")
include(":modules:main:main_screen")
include(":modules:main:1_home")
include(":modules:main:2_catalog")
include(":modules:main:3_orders")
include(":modules:main:4_favorites")
include(":modules:main:5_basket")
include(":modules:common:choosing_city")
include(":modules:common:about_company")
include(":modules:common:brands")
include(":modules:common:pharmacies_map")
include(":modules:common:advantages")
include(":modules:common:partners")
