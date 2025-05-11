plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.kotlin.compiler.plugin.compose)
//    alias(libs.plugins.jetbrains.kotlin.compiler.plugin.compose)

}

android {
    namespace = "com.example.yummibox"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.yummibox"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

buildFeatures{
    viewBinding = true
    compose = true
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
//        sourceCompatibility = JavaVersion.VERSION_1_8
//        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11

    }
    kotlinOptions {
//        jvmTarget = "1.8"
        jvmTarget = "11"

    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
}

dependencies {

    implementation ("com.google.android.material:material:1.9.0")
//    implementation 'androidx.appcompat:appcompat:1.6.1'

//    implementation "androidx.navigation:navigation-fragment-ktx:2.7.3"
//    implementation "androidx.navigation:navigation-ui-ktx:2.7.3"


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

//    implementation(platform(libs.firebase.bom))
//    implementation(platform(libs.firebase.bom))
//    implementation(libs.firebase.auth.ktx)
//    implementation(libs.firebase.database.ktx)
    implementation ("com.google.firebase:firebase-auth-ktx:22.3.1")
    implementation ("com.google.firebase:firebase-database-ktx:21.0.0")

    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.firebase.auth)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    implementation(libs.firebase.storage)
    implementation(libs.firebase.database)
    implementation(libs.androidx.constraintlayout)
//    implementation(libs.constraint.layout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // Navigation Component
    implementation("androidx.navigation:navigation-ui-ktx:2.7.0")

    // External dependency for ImageSlideshow
    implementation("com.github.denzcoskun:ImageSlideshow:0.1.2")

    implementation ("com.google.firebase:firebase-auth-ktx:22.3.1")
    implementation ("com.google.firebase:firebase-database-ktx:21.0.0")

    implementation(libs.play.services.base)
    implementation(libs.play.services.auth)
    implementation(libs.androidx.credentials.play.services.auth.v120alpha01)
    implementation(libs.androidx.credentials.play.services.auth)
//
////    implementation(libs.androidx.credentials.v120alpha01)
////    implementation(libs.androidx.credentials.play.services.auth.v120alpha01)
////    implementation(libs.androidx.credentials.new)
////    implementation(libs.androidx.credentials.play.services)
//
//    implementation(libs.googleid)
////    implementation(libs.firebase.base)
//    implementation(libs.firebase.auth)
//    implementation(libs.firebase.storage)
//
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.activity.compose)

    //    for glide to save selected image in firebase
    implementation ("com.github.bumptech.glide:glide:4.16.0")
////    implementation("com.squareup.okhttp3:okhttp:4.12.0")
//    implementation("com.squareup.okhttp3:okhttp:4.12.0")
//
////    debugImplementation("androidx.compose.ui:ui-tooling")
////    debugImplementation("androidx.compose.ui:ui-test-manifest")


}
