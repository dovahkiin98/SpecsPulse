buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.android.tools.build.gradle)
        classpath(libs.kotlin.gradle)

        classpath(libs.google.services)
        classpath(libs.firebase.crashlytics.gradle)
    }
}

task("clean") {
    delete(rootProject.buildDir)
}