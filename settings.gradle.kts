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
include(":modules:main:4_stocks")
include(":modules:main:5_basket")

//Модуль 'Профиль'.
include(":modules:features:personal_data:feature")
include(":modules:features:personal_data:api")

//Модуль 'Поиска заказа'
include(":modules:features:order_search:feature")
include(":modules:features:order_search:api")

//Модуль 'Выбор города'.
include(":modules:features:choosing_city:feature")
include(":modules:features:choosing_city:api")

//Модуль 'Карта аптек'.
include(":modules:features:pharmacies_map:feature")
include(":modules:features:pharmacies_map:api")

//Модуль 'Детали заказа'.
include(":modules:features:order_details:feature")
include(":modules:features:order_details:api")

//Модуль 'Карточка товара'.
include(":modules:features:product_card:feature")
include(":modules:features:product_card:api")

//Модуль 'Обратная связь'.
include(":modules:features:feedback:feature")
include(":modules:features:feedback:api")

//Модуль 'Оформление заказа'.
include(":modules:features:making_order:feature")
include(":modules:features:making_order:api")

//Модуль 'Работа с нами'.
include(":modules:features:work_with_us:feature")
include(":modules:features:work_with_us:api")

//Модуль 'О компании'.
include(":modules:features:about_company:feature")
include(":modules:features:about_company:api")

//Модуль 'Лицензии и соглашения'.
include(":modules:features:licenses:feature")
include(":modules:features:licenses:api")

//Модуль 'Контакты'.
include(":modules:features:contacts:feature")
include(":modules:features:contacts:api")

//Модуль 'Отзывы'.
include(":modules:features:reviews:feature")
include(":modules:features:reviews:api")

//Модуль 'Нашим партнерам'.
include(":modules:features:our_partners:feature")
include(":modules:features:our_partners:api")

//Модуль 'Сотрудничество'.
include(":modules:features:cooperation:feature")
include(":modules:features:cooperation:api")

//Модуль 'Избранное'.
include(":modules:features:favorites:feature")
include(":modules:features:favorites:api")

//Модуль 'FAQ'.
include(":modules:features:faq:feature")
include(":modules:features:faq:api")

//Модуль 'Благотворительность'.
include(":modules:features:charity:feature")
include(":modules:features:charity:api")

//Модуль 'Сканировани баркода'.
include(":modules:features:barcode_scanner:feature")
include(":modules:features:barcode_scanner:api")

//Модуль 'Бренды и производители'.
include(":modules:features:brands:feature")
include(":modules:features:brands:api")