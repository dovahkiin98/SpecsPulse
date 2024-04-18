// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.tools.build.gradle) apply false
    alias(libs.plugins.kotlin.gradle) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt.android.gradle) apply false
//    alias(libs.plugins.google.services) apply false
//    alias(libs.plugins.firebase.crashlytics) apply false
}