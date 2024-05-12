import org.gradle.util.internal.GUtil.loadProperties

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "${AndroidConfiguration.NAMESPACE}.common"
    compileSdk = AndroidConfiguration.COMPILED_SDK

    defaultConfig {
        minSdk = AndroidConfiguration.MIN_SDK_VERSION
        targetSdk = AndroidConfiguration.TARGET_SDK_VERSION
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    flavorDimensions.add("environment")
    productFlavors {
        val envProperties = loadProperties(rootProject.file("properties/environment.properties"))
        Environment.values()
            .first { it.name ==  envProperties["env"].toString().replace("\"","") }
            .let { env ->
                create(env.name) {
                    dimension = "environment"
                    val configProperties = loadProperties(rootProject.file("properties/config/configuration-${env.name.toLowerCase()}.properties"))
                    buildConfigField("String", "BASE_URL", configProperties["baseUrl"].toString())
                }
            }
    }
}


dependencies {
    implementation(Dependency.Core.KTX_APPCOMPAT)
    implementation(Dependency.Core.KTX_DATABINDING_VIEWBINDING)
    implementation(Dependency.Network.RETROFIT)
    implementation(Dependency.Network.RETROFITKIT)
    implementation(Dependency.Network.RETROFIT_GSON)
    implementation(Dependency.Network.RETROFIT_RX)
    implementation(Dependency.Network.OKHTTP3_LOGGING)
    implementation(Dependency.Network.TIKXML)

    testImplementation(Dependency.Core.JUNIT)
    androidTestImplementation(Dependency.Core.KTX_JUNIT_EXT)
    androidTestImplementation(Dependency.Core.KTX_ESPRESSO)
}

kapt {
    correctErrorTypes = true
}