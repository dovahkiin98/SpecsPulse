import java.time.format.DateTimeFormatter
import java.time.LocalDate

plugins {
    id("com.android.application")
    kotlin("android")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    signingConfigs {
        maybeCreate("testKey").apply {
            keyAlias = "key0"
            keyPassword = "123456"
            storeFile = file("../testkey.jks")
            storePassword = "123456"
        }
    }

    compileSdk = 31

    defaultConfig {
        applicationId = "specspulse.app"

        minSdk = 24
        targetSdk = 31

        vectorDrawables.useSupportLibrary = true
        buildFeatures {
            compose = true
        }

        versionCode = 9

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    flavorDimensions += arrayOf("dev")

    productFlavors {
        maybeCreate("dev").apply {
            dimension = "dev"

            minSdk = 28

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
        getByName("debug") {
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = true

            signingConfig = signingConfigs["testKey"]
        }

        maybeCreate("preRelease").apply {
            isMinifyEnabled = true
            isDebuggable = true

            versionNameSuffix =
                "-${DateTimeFormatter.ofPattern("dd-MM-yyyy").format(LocalDate.now())}"

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            signingConfig = signingConfigs["testKey"]
        }

        getByName("release") {
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs = freeCompilerArgs + arrayOf(
            "-Xallow-jvm-ir-dependencies",
            "-Xskip-prerelease-check",
            "-Xjvm-default=all",
            "-Xopt-in=kotlin.RequiresOptIn",
        )
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
    }
}

dependencies {
    //region Kotlin
    implementation(libs.kotlin.std)
    implementation(libs.kotlinx.coroutines.android)
//    implementation(libs.kotlinx.serialization.json)
    //endregion

    //region AndroidX
    implementation(libs.androidx.core)
    implementation(libs.androidx.core.splashscreen)

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.activity.compose)
    //endregion

    //region Google
    implementation(libs.google.material)
    //endregion

    //region Work
    implementation(libs.androidx.work.runtime)
    //endregion

    //region Network
//    implementation(libs.squareup.okhttp3)
//    implementation(libs.squareup.retrofit2)
    implementation("org.jsoup:jsoup:1.14.2")

//    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")
    //endregion

    //region Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)
    //endregion

    //region Compose
    implementation(libs.bundles.compose)
    implementation(libs.androidx.navigation.compose)

    implementation(libs.androidx.lifecycle.viewmodel.compose)

    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.google.accompanist.insets)
    implementation(libs.google.accompanist.pager)
    implementation(libs.google.accompanist.swiperefresh)

    implementation("io.coil-kt:coil-compose:1.3.2")
    implementation("me.onebone:toolbar-compose:2.2.0")
    //endregion

    coreLibraryDesugaring(libs.android.tools.desugar)
    debugImplementation(libs.androidx.compose.ui.tooling)
}