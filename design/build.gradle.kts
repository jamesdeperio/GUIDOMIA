plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "${AndroidConfiguration.NAMESPACE}.design"
    compileSdk = AndroidConfiguration.COMPILED_SDK

    defaultConfig {
        minSdk = AndroidConfiguration.MIN_SDK_VERSION
        targetSdk = AndroidConfiguration.TARGET_SDK_VERSION
        vectorDrawables.useSupportLibrary = true
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}


dependencies {
    //CORE LIBRARIES
    implementation(project(Module.COMMON))
    implementation(Dependency.Core.KTX_CORE)
    implementation(Dependency.Core.KTX_CORE_SPLASH)
    implementation(Dependency.Core.CONSTRAINT_LAYOUT)
    implementation(Dependency.Core.GOOGLE_MATERIAL)


    //UI
    implementation(Dependency.UI.EPOXY)
    annotationProcessor(Dependency.UI.EPOXY_PROCESSOR)
    kapt(Dependency.UI.EPOXY_PROCESSOR)

    //image processor
    implementation(Dependency.UI.GLIDE)
    kapt (Dependency.UI.GLIDE_COMPILER)
    implementation (Dependency.UI.ANDROID_SVG)
    implementation (Dependency.UI.LOTTIE)
    implementation("com.github.barteksc:android-pdf-viewer:3.2.0-beta.1") {
        exclude(group = "com.android.support", module = "support-v4")
        exclude(group = "com.android.support", module = "support-fragment")
    }
    //anim
    implementation(Dependency.UI.ANDROID_ANIM)
    implementation(Dependency.UI.RECYCLERVIEW_ANIM)
    implementation(Dependency.UI.FB_SHIMMER)
    
    //rx
    implementation(Dependency.RX.RXJAVA)
    implementation(Dependency.RX.RXANDROID)
    implementation(Dependency.RX.RXKOTLIN)

    implementation("androidx.compose.ui:ui:1.6.7")
    implementation("androidx.compose.material:material:1.6.7")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation("androidx.compose.runtime:runtime-livedata:1.6.7")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    implementation("androidx.compose.ui:ui-tooling:1.6.7")


}

kapt {
    correctErrorTypes = true
}