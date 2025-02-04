plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.yummibox"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.yummibox"
        minSdk = 21
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

buildFeatures{
    viewBinding = true
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

//    implementation 'com.google.android.material:material:1.9.0'
//    implementation 'androidx.appcompat:appcompat:1.6.1'

//    implementation "androidx.navigation:navigation-fragment-ktx:2.7.3"
//    implementation "androidx.navigation:navigation-ui-ktx:2.7.3"


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.fragment)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // Navigation Component
    implementation("androidx.navigation:navigation-ui-ktx:2.7.0")

    // External dependency for ImageSlideshow
    implementation("com.github.denzcoskun:ImageSlideshow:0.1.2")

}
