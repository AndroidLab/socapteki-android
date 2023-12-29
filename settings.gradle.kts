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
include(":modules:main:1_home")
include(":modules:main:2_catalog")
include(":modules:main:3_stocks")
include(":modules:main:4_basket")
include(":modules:main:5_menu:_menu")
include(":modules:features:feature_screen")

//Модуль 'Уведомления'.
include(":modules:main:5_menu:notifications")

//Модуль 'О компании'.
include(":modules:main:5_menu:about_company")

//Модуль 'Симптомы и болезни'.
include(":modules:main:5_menu:symptoms_diseases")

//Модуль 'Работа у нас'.
include(":modules:main:5_menu:work_with_us")

//Модуль 'Реферальная программа'.
include(":modules:main:5_menu:referral_program")

//Модуль 'Профиль'.
include(":modules:main:5_menu:profile")

//Модуль 'Контакты'.
include(":modules:main:5_menu:contacts")

//Модуль 'Юридические документы'.
include(":modules:main:5_menu:legal_documents")

//Модуль 'Бренды и производители'.
include(":modules:main:5_menu:brands")

//Модуль 'Благотворительность'.
include(":modules:main:5_menu:charity")

//Модуль 'Покупателям'.
include(":modules:main:5_menu:customers")

//Модуль заказов
include(":modules:main:5_menu:orders")





//Модуль 'Выбор города'.
include(":modules:features:choosing_city:feature")
include(":modules:features:choosing_city:api")

//Модуль 'Карта аптек'.
include(":modules:features:pharmacies_map:feature")
include(":modules:features:pharmacies_map:api")

//Модуль 'Карточка товара'.
include(":modules:features:product_card:feature")
include(":modules:features:product_card:api")

//Модуль 'Обратная связь'.
include(":modules:features:feedback:feature")
include(":modules:features:feedback:api")

//Модуль 'Оформление заказа'.
include(":modules:features:making_order:feature")
include(":modules:features:making_order:api")

//Модуль 'Избранное'.
include(":modules:features:favorites:feature")
include(":modules:features:favorites:api")

//Модуль 'Сканировани баркода'.
include(":modules:features:barcode_scanner:feature")
include(":modules:features:barcode_scanner:api")

