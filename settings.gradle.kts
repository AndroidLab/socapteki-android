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
include(":components")
include(":modules:main:main_screen")
include(":modules:main:main_common")
include(":modules:main:1_home")
include(":modules:main:2_catalog")
include(":modules:main:3_orders")
include(":modules:main:4_favorites")
include(":modules:main:5_basket")
include(":modules:features:about_company")
include(":modules:features:brands")
include(":modules:features:advantages")
include(":modules:features:partners")

//Модуль профиля
include(":modules:features:personal_data:feature")
include(":modules:features:personal_data:api")

//Модуль поиска заказа
include(":modules:features:order_search:feature")
include(":modules:features:order_search:api")

//Модуль выбора города
include(":modules:features:choosing_city:feature")
include(":modules:features:choosing_city:api")

//Модуль карты аптек
include(":modules:features:pharmacies_map:feature")
include(":modules:features:pharmacies_map:api")

//Модуль деталей заказа
include(":modules:features:shared:order_details:feature")
include(":modules:features:shared:order_details:api")

//Модуль карточки товара
include(":modules:features:shared:product_card:feature")
include(":modules:features:shared:product_card:api")