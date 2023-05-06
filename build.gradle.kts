plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    kotlin("multiplatform").version("1.8.20").apply(false)
    id("com.android.application").version("8.0.0").apply(false)
    id("com.android.library").version("8.0.0").apply(false)
    id("org.jetbrains.compose").version("1.4.0").apply(false)
    kotlin("android").version("1.8.20").apply(false)
    kotlin("plugin.serialization").version("1.8.20").apply(false)
    kotlin("native.cocoapods").version("1.8.20").apply(false)
    id("app.cash.sqldelight").version("2.0.0-alpha05").apply(false)
    id("io.github.skeptick.libres").version("1.1.8").apply(false)
}
