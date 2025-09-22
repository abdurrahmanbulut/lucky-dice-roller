plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("kapt")
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ktlint)
}

android {
    namespace = "com.bulut.luckyDiceRoller"
    compileSdk = 36
    defaultConfig.minSdk = 24
    defaultConfig.targetSdk = 36
    defaultConfig.versionCode = 3
    defaultConfig.versionName = "1.2"
    defaultConfig.testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    kotlin.jvmToolchain(17)
    buildFeatures.compose = true
    packaging.resources.excludes += "META-INF/{AL2.0,LGPL2.1}"

    defaultConfig {
        applicationId = "com.bulut.luckyDiceRoller"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.navigation)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.lottie)
    implementation(libs.glide)
    implementation(libs.datastore)

    implementation(fileTree("../libs"))
    implementation(libs.androidx.constraintlayout)

    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
