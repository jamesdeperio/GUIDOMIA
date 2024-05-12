import org.gradle.util.internal.GUtil.loadProperties

plugins {
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.android.application")
    id("com.google.dagger.hilt.android")
    id("io.objectbox")// Apply last.
}

kapt {
    correctErrorTypes = true
}
hilt {
    enableAggregatingTask = true
}
android {
    namespace = "${AndroidConfiguration.NAMESPACE}.androiddemo"
    compileSdk = AndroidConfiguration.COMPILED_SDK

    defaultConfig {
        applicationId = "${AndroidConfiguration.NAMESPACE}.androiddemo"
        minSdk = AndroidConfiguration.MIN_SDK_VERSION
        targetSdk = AndroidConfiguration.TARGET_SDK_VERSION
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            // signingConfig = signingConfigs.getByName("release")
        }
        getByName("debug") {
            isDebuggable = true
        }
    }

    flavorDimensions.add("environment")
    productFlavors {
        val envProperties = loadProperties(rootProject.file("properties/environment.properties"))
        Environment.values()
            .first { it.name ==  envProperties["env"].toString().replace("\"","") }
            .let { env ->
            create(env.name) {
                dimension = "environment"
                versionCode = getVersionCode(env)
                versionName = getVersionName(env)

                if (env != Environment.PROD) {
                    applicationIdSuffix = getSuffix(env)
                }
            }
        }
    }

    applicationVariants.all {
        val variant = this
        variant.outputs.map { it as com.android.build.gradle.internal.api.BaseVariantOutputImpl }
            .forEach { output ->
                val outputFileName = "myapp-${variant.flavorName}-${variant.versionName}.apk"
                output.outputFileName = outputFileName
            }
    }

    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    packagingOptions {
        resources {
            excludes += listOf("META-INF/*.kotlin_module", "META-INF/DEPENDENCIES")
        }
    }
    buildFeatures {
        viewBinding = true
        compose = true
    }
}

dependencies {
    implementation(project(Module.DESIGN))
    implementation(project(Module.COMMON))

    implementation(Dependency.Core.KTX_CORE_SPLASH)
    implementation(Dependency.Core.KTX_MULTIDEX)
    implementation(Dependency.Core.KTX_LIFECYCLE_LIVEDATA)
    implementation(Dependency.Core.KTX_LIFECYCLE_VIEWMODEL)
    implementation(Dependency.Core.KTX_NAVIGATION_FRAGMENT)
    implementation(Dependency.Core.KTX_NAVIGATION_UI)
    implementation(Dependency.Core.CONSTRAINT_LAYOUT)
    implementation(Dependency.Core.MAVEN_ARTIFACT)
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    implementation(Dependency.Core.KTX_ACTIVITY)

    implementation(Dependency.RX.RXJAVA)
    implementation(Dependency.RX.RXANDROID)
    implementation(Dependency.RX.RXKOTLIN)
    
    implementation(Dependency.Network.RETROFIT)
    implementation(Dependency.Network.RETROFITKIT)
    implementation(Dependency.Network.RETROFIT_GSON)
    implementation(Dependency.Network.RETROFIT_RX)
    implementation(Dependency.Network.OKHTTP3_LOGGING)
    implementation(Dependency.Network.TIKXML)

    implementation(Dependency.Injection.DAGGER)
    implementation("androidx.navigation:navigation-compose:2.7.7")
    kapt(Dependency.Injection.DAGGER_COMPILER)

    //UI
    implementation(Dependency.UI.EPOXY)
    annotationProcessor(Dependency.UI.EPOXY_PROCESSOR)
    kapt(Dependency.UI.EPOXY_PROCESSOR)

    testImplementation(Dependency.Core.JUNIT)
    androidTestImplementation(Dependency.Core.KTX_JUNIT_EXT)
    androidTestImplementation(Dependency.Core.KTX_ESPRESSO)

    // Mockito-Kotlin
    testImplementation(Dependency.RX.RXJAVA)
    testImplementation(Dependency.RX.RXANDROID)
    testImplementation(Dependency.RX.RXKOTLIN)

    // RxJava2
    // For LiveData and ViewModel testing (included if you're using AndroidX)
    testImplementation("org.mockito:mockito-core:3.11.2")
    testImplementation("androidx.arch.core:core-testing:2.2.0")

    implementation("androidx.compose.ui:ui:1.6.7")
    implementation("androidx.compose.material:material:1.6.7")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation("androidx.compose.runtime:runtime-livedata:1.6.7")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    implementation("androidx.compose.ui:ui-tooling:1.6.7")


  //  implementation("androidx.room:room-runtime:2.6.1")
  //  kapt("androidx.room:room-compiler:2.6.1")
  //  implementation("androidx.room:room-rxjava3:2.6.1")


    implementation("io.objectbox:objectbox-android:3.8.0")
    kapt("io.objectbox:objectbox-processor:3.8.0")



}