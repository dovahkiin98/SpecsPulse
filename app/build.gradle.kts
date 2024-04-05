@file:Suppress("UnstableApiUsage")

import java.time.LocalDate
import java.time.format.DateTimeFormatter

plugins {
    alias(libs.plugins.android.tools.build.gradle)
    alias(libs.plugins.kotlin.gradle)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android.gradle)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
}

android {
    namespace = "specspulse.app"

    signingConfigs {
        maybeCreate("testKey").apply {
            keyAlias = "key0"
            keyPassword = "123456"
            storeFile = file("../testkey.jks")
            storePassword = "123456"
        }
    }

    compileSdk = 34

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    defaultConfig {
        applicationId = "specspulse.app"

        minSdk = 24
        targetSdk = 34

        vectorDrawables.useSupportLibrary = true

        buildFeatures {
            compose = true
            buildConfig = true
        }

        versionCode = 10

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    flavorDimensions += "dev"

    productFlavors {
        maybeCreate("dev").apply {
            dimension = "dev"

            minSdk = 30

            versionNameSuffix =
                "-dev-${DateTimeFormatter.ofPattern("dd-MM-yyyy").format(LocalDate.now())}"

            buildConfigField("Boolean", "DEV", "true")
            resourceConfigurations += arrayOf("en", "xxhdpi")
        }

        maybeCreate("deploy").apply {
            dimension = "dev"

            versionName = "3.0"
            minSdk = 24

            buildConfigField("Boolean", "DEV", "false")
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = true

            signingConfig = signingConfigs["testKey"]
        }

        maybeCreate("preRelease").apply {
            isMinifyEnabled = true

            versionNameSuffix =
                "-${DateTimeFormatter.ofPattern("dd-MM-yyyy").format(LocalDate.now())}"

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            signingConfig = signingConfigs["testKey"]
        }

        release {
            isMinifyEnabled = true
            isDebuggable = false

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            signingConfig = signingConfigs["testKey"]
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs = freeCompilerArgs + arrayOf(
            "-Xskip-prerelease-check",
            "-Xopt-in=kotlin.RequiresOptIn",
        )
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }
}

dependencies {
    //region Kotlin
    implementation(libs.kotlin.std)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.test)
//    implementation(libs.kotlinx.serialization.json)
    //endregion

    //region AndroidX
    implementation(libs.androidx.core)
    implementation(libs.androidx.core.splashscreen)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)

    implementation(libs.androidx.preferences)
    implementation(libs.androidx.datastore.preferences)
    //endregion

    //region Network
    implementation(libs.jsoup)
    implementation(libs.coil.compose)
    //endregion

    //region Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)
    //endregion

    //region Compose
    implementation(libs.bundles.compose)
    implementation(libs.androidx.compose.material3)

    implementation(libs.androidx.lifecycle.viewmodel.compose)
    //endregion

    //region Hilt
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    ksp(libs.androidx.hilt.compiler)
    //endregion

    //region Test
    testImplementation(libs.junit.api)
    testImplementation(libs.google.truth)
    testImplementation(libs.mockk)
    //endregion

    //region Android Test
    androidTestImplementation(libs.mockk.android)
    androidTestImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.ext.truth)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.rules)

    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.compose.ui.test.manifest)
    androidTestImplementation(libs.hilt.android.testing)

    androidTestImplementation(libs.androidx.espresso.core)
    //endregion

    coreLibraryDesugaring(libs.desugar.jdk.libs)
    debugImplementation(libs.androidx.compose.ui.tooling)
}