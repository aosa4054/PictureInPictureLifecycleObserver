val kotlin_version: String by extra
plugins {
    id("com.android.library")
}
apply {
    plugin("kotlin-android")
    plugin("kotlin-android-extensions")
}

repositories {
    mavenCentral()
}

android {
    compileSdkVersion(28)


    defaultConfig {
        minSdkVersion(24)
        targetSdkVersion(28)
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to arrayOf("*.jar"))))

    implementation("androidx.appcompat:appcompat:1.0.2")
    implementation("androidx.core:core-ktx:1.0.2")
}