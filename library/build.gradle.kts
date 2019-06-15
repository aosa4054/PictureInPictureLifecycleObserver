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
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")        }
    }

}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to arrayOf("*.jar"))))

    implementation("androidx.appcompat:appcompat:1.0.2")
    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test:runner:1.2.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")
    implementation("androidx.core:core-ktx:1.0.2")
    implementation("com.github.aosa4054:PictureInPictureLifecycleObserver:1.0.0")
}