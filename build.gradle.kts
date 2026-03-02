plugins {
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.vanniktech.mavenPublish) apply false

    kotlin("plugin.serialization") version "2.3.0" apply false
    id("org.jetbrains.kotlinx.binary-compatibility-validator") version "0.17.0" apply false

    id("org.jetbrains.dokka") version "2.1.0"
}

subprojects {
    apply(plugin = "org.jetbrains.dokka")
}
