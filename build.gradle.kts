buildscript {
    repositories {
        google()
        jcenter()
        maven ("http://dl.bintray.com/kotlin/kotlin-dev")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.4.0-alpha10")
        classpath(kotlin("gradle-plugin", "1.3.30-dev-576"))

        classpath("com.google.gms:google-services:4.2.0")
    }
}

allprojects {
    repositories {
        jcenter()
        google()
        maven("https://jitpack.io")
        maven("http://dl.bintray.com/kotlin/kotlin-dev")

    }
}

tasks.register("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}