import java.text.SimpleDateFormat

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
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

    compileSdkVersion(30)

    defaultConfig {
        applicationId = "specspulse.app"

        minSdkVersion(21)
        targetSdkVersion(30)

        vectorDrawables.useSupportLibrary = true
        buildFeatures {
            dataBinding = true
//            compose = true
        }

        versionCode = 8

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    flavorDimensions("dev")

    productFlavors {
        maybeCreate("dev").apply {
            dimension = "dev"

            versionName = "3.0-D"

            minSdkVersion(28)

            versionNameSuffix = "-dev" + "-" + SimpleDateFormat("dd-MM-yyyy").format(System.currentTimeMillis())

            buildConfigField("boolean", "DEV", "true")
            buildConfigField("boolean", "ADS", "false")
            resConfigs("en", "xxhdpi")
        }

        maybeCreate("deploy").apply {
            dimension = "dev"

            versionName = "3.0"

            minSdkVersion(21)

            buildConfigField("boolean", "DEV", "false")
            buildConfigField("boolean", "ADS", "true")
        }
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = true
        }

        maybeCreate("preRelease").apply {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = true
            versionNameSuffix = "-" + SimpleDateFormat("dd-MM-yyyy").format(System.currentTimeMillis())

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs["testKey"]
        }

        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs["testKey"]
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
        // useIR = true
        // freeCompilerArgs = freeCompilerArgs + arrayOf(
        //     "-Xallow-jvm-ir-dependencies",
        //     "-Xskip-prerelease-check",
        //     "-Xopt-in=kotlin.RequiresOptIn"
        // )
    }

    composeOptions {
        // kotlinCompilerVersion = "1.4.10"
        // kotlinCompilerExtensionVersion = "1.0.0-alpha05"
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coRoutines}")

    //region AndroidX
    implementation("androidx.core:core-ktx:${Versions.core}")
    implementation("androidx.appcompat:appcompat:${Versions.appCompat}")
    implementation("androidx.activity:activity-ktx:${Versions.activity}")
    implementation("androidx.preference:preference-ktx:${Versions.preferences}")
    implementation("androidx.fragment:fragment-ktx:${Versions.fragment}")
    //endregion

    //region UI Components
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefreshLayout}")
    implementation("androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}")
    implementation("androidx.recyclerview:recyclerview:${Versions.recyclerView}")
    implementation("androidx.viewpager2:viewpager2:${Versions.viewPager2}")
    implementation("com.google.android.material:material:${Versions.materialComponents}")
    //endregion

    //region Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:${Versions.navigationComponent}")
    implementation("androidx.navigation:navigation-ui-ktx:${Versions.navigationComponent}")
    //endregion

    //region Lifecycle Components
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}")
    //endregion

    //region Google Services
    implementation("com.google.android.gms:play-services-ads:${Versions.googleAdsServices}")
//    implementation("com.google.android.gms:play-services-auth:${Versions.googleAuth}")

    implementation("com.google.firebase:firebase-analytics-ktx:${Versions.firebaseAnalytics}")
    implementation("com.google.firebase:firebase-messaging-ktx:${Versions.firebaseMessaging}")
    implementation("com.google.firebase:firebase-crashlytics-ktx:${Versions.firebaseCrashlytics}")
    //endregion

    //region Networking
    implementation("com.squareup.okhttp3:okhttp:${Versions.okHttp}")

    implementation("com.squareup.retrofit2:retrofit:${Versions.retrofit}")
    implementation("com.squareup.retrofit2:converter-moshi:${Versions.retrofit}")

    implementation("io.coil-kt:coil:${Versions.coil}")

    implementation("com.squareup.moshi:moshi-kotlin:${Versions.moshi}")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}")
    //endregion

    //region Room
    implementation("androidx.room:room-runtime:${Versions.room}")
    implementation("androidx.room:room-ktx:${Versions.room}")
    kapt("androidx.room:room-compiler:${Versions.room}")
    //endregion
}