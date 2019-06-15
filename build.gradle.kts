// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val kotlin_version by extra("1.3.31")
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.4.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
        classpath("com.github.dcendents:android-maven-gradle-plugin:2.0")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven { url = java.net.URI.create("https://jitpack.io") }
    }
}

tasks.create("clean", type = Delete::class) {
    delete(rootProject.buildDir)
}

