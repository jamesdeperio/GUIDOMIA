// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "7.4.2" apply false
    id("com.android.library") version "7.4.2" apply false
    id("org.jetbrains.kotlin.android") version "1.7.0" apply false
    id("org.jetbrains.kotlin.jvm") version "1.7.0" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false

}

buildscript {
    val objectboxVersion by extra("3.8.0") // For KTS build scripts

    repositories {
        mavenCentral()
        // Note: 2.9.0 and older are available on jcenter()
    }

    dependencies {
        // Android Gradle Plugin 4.1.0 or later supported.
        classpath("com.android.tools.build:gradle:7.4.2")
        classpath("io.objectbox:objectbox-gradle-plugin:$objectboxVersion")
    }
}
