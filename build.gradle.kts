buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.gradle}")
        classpath(kotlin("gradle-plugin", Versions.kotlin))

        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigationComponent}")

        classpath("com.google.gms:google-services:${Versions.googleServicesGradle}")
        classpath("com.google.firebase:firebase-crashlytics-gradle:${Versions.firebaseCrashlyticsGradle}")
    }
}

allprojects {
    repositories {
        jcenter()
        google()
        maven("https://jitpack.io")
    }
}

tasks.register("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}