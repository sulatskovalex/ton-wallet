rootProject.name = "TonWallet"

include(":androidApp")

include(":shared")

include(":ton-kotlin-adnl")
project(":ton-kotlin-adnl").projectDir = File("../ton-kotlin/ton-kotlin-adnl")

include(":ton-kotlin-api")
project(":ton-kotlin-api").projectDir = File("../ton-kotlin/ton-kotlin-api")

include(":ton-kotlin-bigint")
project(":ton-kotlin-bigint").projectDir = File("../ton-kotlin/ton-kotlin-bigint")

include(":ton-kotlin-bitstring")
project(":ton-kotlin-bitstring").projectDir = File("../ton-kotlin/ton-kotlin-bitstring")

include(":ton-kotlin-block")
project(":ton-kotlin-block").projectDir = File("../ton-kotlin/ton-kotlin-block")

include(":ton-kotlin-boc")
project(":ton-kotlin-boc").projectDir = File("../ton-kotlin/ton-kotlin-boc")

include(":ton-kotlin-cell")
project(":ton-kotlin-cell").projectDir = File("../ton-kotlin/ton-kotlin-cell")

include(":ton-kotlin-crypto")
project(":ton-kotlin-crypto").projectDir = File("../ton-kotlin/ton-kotlin-crypto")

include(":ton-kotlin-hashmap")
project(":ton-kotlin-hashmap").projectDir = File("../ton-kotlin/ton-kotlin-hashmap")

include(":ton-kotlin-liteapi")
project(":ton-kotlin-liteapi").projectDir = File("../ton-kotlin/ton-kotlin-liteapi")

include(":ton-kotlin-liteclient")
project(":ton-kotlin-liteclient").projectDir = File("../ton-kotlin/ton-kotlin-liteclient")

include(":ton-kotlin-logger")
project(":ton-kotlin-logger").projectDir = File("../ton-kotlin/ton-kotlin-logger")

include(":ton-kotlin-mnemonic")
project(":ton-kotlin-mnemonic").projectDir = File("../ton-kotlin/ton-kotlin-mnemonic")

include(":ton-kotlin-contract")
project(":ton-kotlin-contract").projectDir = File("../ton-kotlin/ton-kotlin-contract")

include(":ton-kotlin-tl")
project(":ton-kotlin-tl").projectDir = File("../ton-kotlin/ton-kotlin-tl")

include(":ton-kotlin-tlb")
project(":ton-kotlin-tlb").projectDir = File("../ton-kotlin/ton-kotlin-tlb")

include(":ton-kotlin-fift")
project(":ton-kotlin-fift").projectDir = File("../ton-kotlin/ton-kotlin-fift")

include(":ton")
project(":ton").projectDir = File("../ton-kotlin/ton")

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
