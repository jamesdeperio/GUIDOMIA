import org.gradle.api.Project
import java.io.File
import java.io.FileInputStream
import java.util.*

object AndroidConfiguration {
    const val VERSION_CODE = 1
    const val VERSION = 1
    const val COMPILED_SDK = 34
    const val MIN_SDK_VERSION = 28
    const val TARGET_SDK_VERSION = 34
    const val NAMESPACE = "com.example"
}
enum class Environment {
    MOCK,
    DEV,
    QAT,
    UAT,
    STAGING,
    PROD
}

fun Project.getSuffix(env: Environment): String {
    return ".${env.name.toLowerCase()}"
}

fun Project.getVersionName(env: Environment): String {
    val versionPropertiesFile = getVersionPropertyFile(env)
    val versionProperties = Properties()
    versionProperties.load(FileInputStream(versionPropertiesFile))

    val major = versionProperties["major"]
    val minor = versionProperties["minor"]
    val patch = versionProperties["patch"]

    return "$major.$minor.$patch"
}

fun Project.getVersionCode(env: Environment): Int {
    val versionPropertiesFile = getVersionPropertyFile(env)
    val versionProperties = Properties()
    versionProperties.load(FileInputStream(versionPropertiesFile))

    val major = Integer.parseInt(versionProperties["major"] as String)
    val minor = Integer.parseInt(versionProperties["minor"] as String)
    val patch = Integer.parseInt(versionProperties["patch"] as String)

    return major * 1000000 + minor * 1000 + patch
}

fun Project.getVersionPropertyFile(env: Environment): File
= rootProject.file("properties/version/app_version_${env.name.toLowerCase()}.properties")