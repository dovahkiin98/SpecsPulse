plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("com.google.gms.google-services")
//    id("com.getkeepsafe.dexcount")
}

android {
    compileSdkVersion(28)
    dataBinding.isEnabled = true
    defaultConfig {
        applicationId = "specspulse.app"
        minSdkVersion(19)
        targetSdkVersion(28)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
//            isDebuggable = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
//            signingConfig = signingConfigs.getByName("config")
        }
    }
    flavorDimensions("develop")
    productFlavors {
        create("publish") {
            minSdkVersion(19)
            versionCode = 8
            versionName = "3.0"
            buildConfigField("Boolean", "ADS", "true")
            setDimension("develop")
        }
        create("development") {
            minSdkVersion(26)
            versionCode = 8
            versionName = "3.0-D"
            buildConfigField("Boolean", "ADS", "false")
            resConfigs("en", "xxhdpi")
            setDimension("develop")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    kapt("androidx.databinding:databinding-compiler:3.4.0-alpha10")

    implementation("androidx.lifecycle:lifecycle-extensions:2.1.0-alpha01")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.1.0-alpha01")
    implementation("androidx.lifecycle:lifecycle-livedata:2.1.0-alpha01")
    kapt("androidx.lifecycle:lifecycle-compiler:2.1.0-alpha01")

    implementation(kotlin("stdlib-jdk8", "1.3.30-dev-576"))
    implementation("androidx.core:core-ktx:1.1.0-alpha03")
    implementation("androidx.appcompat:appcompat:1.1.0-alpha01")
//    implementation("androidx.fragment:fragment-ktx:1.1.0-alpha03")
    implementation("androidx.recyclerview:recyclerview:1.1.0-alpha01")
    implementation("com.google.android.material:material:1.1.0-alpha02")
    implementation("androidx.constraintlayout:constraintlayout:2.0.0-alpha3")

    implementation("com.squareup.retrofit2:retrofit:2.5.0")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.5.0")
    implementation("com.squareup.retrofit2:converter-gson:2.5.0")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.0")
    implementation("io.reactivex.rxjava2:rxjava:2.2.5")
    implementation("com.squareup.okhttp3:okhttp:3.12.1")
    implementation("com.squareup.picasso:picasso:2.71828")
    implementation("com.google.code.gson:gson:2.8.5")

    implementation("org.jetbrains.anko:anko-commons:0.10.8")
    implementation("com.github.chrisbanes:PhotoView:2.0.0")
    implementation("com.google.firebase:firebase-ads:17.1.2")

    androidTestImplementation("androidx.test:core:1.1.0")
    androidTestImplementation ("androidx.test:runner:1.1.1")
    androidTestImplementation ("androidx.test.ext:junit:1.1.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.1.1")
}