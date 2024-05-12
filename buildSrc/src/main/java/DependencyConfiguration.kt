import org.gradle.kotlin.dsl.provideDelegate

object Version {
    object Core {
        const val KTX_CORE = "1.7.0"
        const val KTX_CORE_SPLASH = "1.0.0-beta02"
        const val KTX_ACTIVITY = "1.7.2"
        const val KTX_APPCOMPAT = "1.4.1"
        const val KTX_LIFECYCLE = "2.6.2"
        const val KTX_DATABINDING_VIEWBINDING = "8.1.2"
        const val KTX_NAVIGATION = "2.6.0"
        const val GOOGLE_MATERIAL = "1.5.0"
        const val CONSTRAINT_LAYOUT = "2.1.3"
        const val JUNIT = "4.13.2"
        const val KTX_JUNIT_EXT = "1.1.5"
        const val KTX_ESPRESSO = "3.5.1"
        const val KTX_MULTIDEX = "2.0.1"
        const val MAVEN_ARTIFACT = "3.9.5"
    }
    object UI {
        const val PDF_IMAGE = "3.2.0-beta.1"
        const val EPOXY = "4.6.2"
        const val FB_SHIMMER = "0.1.0@aar"
        const val GLIDE = "4.13.2"
        const val ANDROID_SVG = "1.4"
        const val ANDROID_ANIM = "2.4@aar"
        const val RECYCLERVIEW_ANIM = "4.0.2"
        const val LOTTIE = "6.1.0"
    }
    
    object RX {
        const val RXJAVA = "3.1.8"
        const val RXANDROID = "3.0.2"
        const val RXKOTLIN = "3.0.1"
    }

    object Injection {
        const val DAGGER = "2.44"
    }

    object Network {
     const val KTOR = "2.3.6"
     const val KTORFIT = "1.10.1"
     const val RETROFIT = "2.9.0"
     const val RETROFITKIT = "v1.0.5"
     const val OKHTTP3_LOGGING = "4.9.1"
     const val TIKXML = "0.8.13"
    }
}

object Dependency {
    object Core {
        val KTX_CORE by lazy { "androidx.core:core-ktx:${Version.Core.KTX_CORE}" }
        val KTX_CORE_SPLASH by lazy { "androidx.core:core-splashscreen:${Version.Core.KTX_CORE_SPLASH}" }
        val KTX_ACTIVITY by lazy { "androidx.activity:activity-ktx:${Version.Core.KTX_ACTIVITY}" }
        val KTX_FRAGMENT by lazy { "androidx.activity:fragment-ktx:${Version.Core.KTX_ACTIVITY}" }
        val KTX_APPCOMPAT by lazy { "androidx.appcompat:appcompat:${Version.Core.KTX_APPCOMPAT}" }
        val KTX_LIFECYCLE_LIVEDATA by lazy { "androidx.lifecycle:lifecycle-livedata-ktx:${Version.Core.KTX_LIFECYCLE}" }
        val KTX_LIFECYCLE_VIEWMODEL by lazy { "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.Core.KTX_LIFECYCLE}" }
        val KTX_DATABINDING_VIEWBINDING by lazy { "androidx.databinding:viewbinding:${Version.Core.KTX_DATABINDING_VIEWBINDING}" }
        val KTX_NAVIGATION_FRAGMENT by lazy { "androidx.navigation:navigation-fragment-ktx:${Version.Core.KTX_NAVIGATION}" }
        val KTX_NAVIGATION_UI by lazy { "androidx.navigation:navigation-ui-ktx:${Version.Core.KTX_NAVIGATION}" }
        val CONSTRAINT_LAYOUT by lazy { "androidx.constraintlayout:constraintlayout:${Version.Core.CONSTRAINT_LAYOUT}" }
        val GOOGLE_MATERIAL by lazy {  "com.google.android.material:material:${Version.Core.GOOGLE_MATERIAL}" }
        val JUNIT by lazy { "junit:junit:${Version.Core.JUNIT}" }
        val KTX_JUNIT_EXT by lazy {  "androidx.test.ext:junit:${Version.Core.KTX_JUNIT_EXT}" }
        val KTX_ESPRESSO by lazy {  "androidx.test.espresso:espresso-core:${Version.Core.KTX_ESPRESSO}" }
        val KTX_MULTIDEX by lazy {  "androidx.multidex:multidex:${Version.Core.KTX_MULTIDEX}" }
        val MAVEN_ARTIFACT by lazy {  "org.apache.maven:maven-artifact:${Version.Core.MAVEN_ARTIFACT}" }
    }
    object UI {
        val EPOXY by lazy {  "com.airbnb.android:epoxy:${Version.UI.EPOXY}" }
        val EPOXY_PROCESSOR by lazy { "com.airbnb.android:epoxy-processor:${Version.UI.EPOXY}" }
        val FB_SHIMMER by lazy { "com.facebook.shimmer:shimmer:${Version.UI.FB_SHIMMER}" }
        val GLIDE by lazy { "com.github.bumptech.glide:glide:${Version.UI.GLIDE}" }
        val GLIDE_OKHTTP by lazy { "com.github.bumptech.glide:okhttp3-integration:${Version.UI.GLIDE}" }
        val GLIDE_TRANSFORMATIONS by lazy { "com.github.bumptech.glide:glide-transformations:${Version.UI.GLIDE}" }
        val GLIDE_PDF_DECODER by lazy { "com.github.bumptech.glide:glide-pdfdecoder:${Version.UI.GLIDE}" }
        val GLIDE_COMPILER by lazy { "com.github.bumptech.glide:compiler:${Version.UI.GLIDE}" }
        val ANDROID_SVG by lazy { "com.caverock:androidsvg-aar:${Version.UI.ANDROID_SVG}" }
        val ANDROID_ANIM by lazy { "com.daimajia.androidanimations:library:${Version.UI.ANDROID_ANIM}" }
        val RECYCLERVIEW_ANIM by lazy { "jp.wasabeef:recyclerview-animators:${Version.UI.RECYCLERVIEW_ANIM}" }
        val LOTTIE by lazy { "com.airbnb.android:lottie:${Version.UI.LOTTIE}" }
        val PDF_IMAGE by lazy { "com.github.barteksc:android-pdf-viewer:${Version.UI.PDF_IMAGE}"}
        val PSPDFKIT by lazy { " com.pspdfkit:pspdfkit:10.5.2'"}
    }

    object RX {
        val RXJAVA by lazy { "io.reactivex.rxjava3:rxjava:${Version.RX.RXJAVA}" }
        val RXANDROID by lazy { "io.reactivex.rxjava3:rxandroid:${Version.RX.RXANDROID}" }
        val RXKOTLIN by lazy { "io.reactivex.rxjava3:rxkotlin:${Version.RX.RXKOTLIN}" }
    }

    object Injection {
        val DAGGER by lazy { "com.google.dagger:hilt-android:${Version.Injection.DAGGER}" }
        val DAGGER_COMPILER by lazy { "com.google.dagger:hilt-compiler:${Version.Injection.DAGGER}" }
    }

    object Network {
        val RETROFIT by lazy { "com.squareup.retrofit2:retrofit:${Version.Network.RETROFIT}" }
        val RETROFITKIT by lazy { "com.github.jamesdeperio:RetrofitKit:${Version.Network.RETROFITKIT}" }
        val OKHTTP3_LOGGING by lazy { "com.squareup.okhttp3:logging-interceptor:${Version.Network.OKHTTP3_LOGGING}" }
        val RETROFIT_RX by lazy { "com.squareup.retrofit2:adapter-rxjava3:${Version.Network.RETROFIT}" }
        val RETROFIT_GSON by lazy { "com.squareup.retrofit2:converter-gson:${Version.Network.RETROFIT}" }
        val TIKXML by lazy { "com.tickaroo.tikxml:retrofit-converter:${Version.Network.TIKXML}" }
    }


}

object Module {
    val COMMON by lazy { mapOf("path" to ":common") }
    val DESIGN by lazy { mapOf("path" to ":design") }
}