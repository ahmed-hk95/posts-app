import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias (libs.plugins.android.application)
    alias (libs.plugins.kotlin.android)
    alias (libs.plugins.compose.compiler)
    alias (libs.plugins.kotlin.serialization)
    alias (libs.plugins.hilt)
    alias (libs.plugins.kotlin.ksp)
}

android {
    namespace = "ahmed.hk.posts"
    compileSdk = 34

    defaultConfig {
        applicationId = "ahmed.hk.posts"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        dataBinding =  true
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    kotlin{
        compilerOptions{
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}


dependencies {
    implementation (libs.bundles.navigation)
    implementation (libs.bundles.lifecycle)
    implementation (libs.google.material)
    implementation (platform(libs.compose.bom))
    implementation (libs.bundles.compose)

    implementation (libs.retrofit)
    implementation (libs.retrofit.converter.kotlinx)
    implementation (libs.okhttp3)
    implementation (libs.kotlinx.serialization.json)

    implementation (libs.bundles.room)
    ksp (libs.room.compiler)

    implementation (libs.hilt.android)
    ksp (libs.hilt.compiler)

    testImplementation (libs.junit)
    androidTestImplementation (libs.bundles.test)

    implementation (libs.core)
    implementation (libs.appCompat)
}