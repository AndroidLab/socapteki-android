@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id(libs.plugins.applicationConventionPlugin.get().pluginId)
    id(libs.plugins.kotlinConventionPlugin.get().pluginId)
    id(libs.plugins.hiltConventionPlugin.get().pluginId)
    id(libs.plugins.navigationConventionPlugin.get().pluginId)
    id(libs.plugins.retrofitConventionPlugin.get().pluginId)
    id(libs.plugins.googleServicesConventionPlugin.get().pluginId)
}

private val major = 0
private val minor = 1
private val patch = 0

android {
    namespace = "ru.apteka.social"

    defaultConfig {
        applicationId = "ru.apteka.social"
        versionCode = major * 10000 + minor * 100 + patch
        versionName = "${major}.${minor}.${patch}"
    }
}

dependencies {
    implementation(project(":components"))
    implementation(project(":modules:main:main_screen"))
    implementation(project(":modules:main:1_home"))
    implementation(project(":modules:main:2_catalog"))
    implementation(project(":modules:main:3_orders"))
    implementation(project(":modules:main:4_stocks"))
    implementation(project(":modules:main:5_basket"))
    implementation(project(":modules:features:personal_data:feature"))
    implementation(project(":modules:features:choosing_city:feature"))
    implementation(project(":modules:features:order_details:feature"))
    implementation(project(":modules:features:product_card:feature"))
    implementation(project(":modules:features:order_search:feature"))
    implementation(project(":modules:features:pharmacies_map:api"))
    implementation(project(":modules:features:pharmacies_map:feature"))
    implementation(project(":modules:features:making_order:feature"))
    implementation(project(":modules:features:about_company:feature"))
    implementation(project(":modules:features:licenses:feature"))
    implementation(project(":modules:features:work_with_us:feature"))
    implementation(project(":modules:features:contacts:feature"))
    implementation(project(":modules:features:reviews:feature"))
    implementation(project(":modules:features:our_partners:feature"))
    implementation(project(":modules:features:cooperation:feature"))
    implementation(project(":modules:features:favorites:feature"))
    implementation(project(":modules:features:faq:feature"))
    implementation(project(":modules:features:charity:feature"))
    implementation(project(":modules:features:feedback:feature"))
    implementation(project(":modules:features:barcode_scanner:feature"))
    implementation(project(":modules:features:brands:feature"))

    //Google & Android
    implementation(libs.coreKtx)
    implementation(libs.constraintlayout)
    implementation(libs.material)
    implementation(libs.gson)
    implementation(libs.coreSplashscreen)
    implementation(libs.bundles.lifecycleDeps)
    implementation(libs.mapsMobile)
    implementation(libs.measured)
}