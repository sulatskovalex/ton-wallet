rootProject.name = "TonWallet"

include(":androidApp")
include(":shared")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
    }

    plugins {
        kotlin("multiplatform").version("1.8.20")
        kotlin("android").version("1.8.20")
        kotlin("native.cocoapods").version("1.8.20")
        id("com.android.application").version("8.0.0")
        id("com.android.library").version("8.0.0")
        id("org.jetbrains.compose").version("1.4.0")
        kotlin("plugin.serialization").version("1.8.20")
        id("io.github.skeptick.libres").version("1.1.8")
        id("app.cash.sqldelight").version("2.0.0-alpha05")
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
    versionCatalogs {
        create("libs") {
            from(files("libs.versions.toml"))
        }
    }
}
//include(":res")
