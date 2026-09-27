plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.dagger.hilt)
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
    alias(libs.plugins.kotlin.android)
//    id("com.google.gms.google-services")
}

android {
    namespace = "com.tawajood.the_community_user"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.tawajood.zilva_provider"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
//    kotlinOptions {
//        jvmTarget = "11"
//    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    // navigation
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    // viewmodel
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    // icons
    implementation(libs.androidx.material.icons.extended)
    // serialization
    implementation(libs.kotlinx.serialization.json)
    // dagger-hilt
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    // retrofit
    implementation(libs.retrofit)
    // moshi
    implementation(libs.com.squareup.moshi.moshi)
    implementation(libs.moshi.kotlin)
    implementation(libs.converter.moshi)
    // coil
    implementation(libs.coil.compose)
    implementation(libs.coil.gif)
    // splash screen
    implementation(libs.androidx.core.splashscreen)
    // paging
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.paging.compose)
    // pager
    // implementation(libs.accompanist.pager)
    // system ui controller
    // implementation(libs.accompanist.systemuicontroller)
    // data store
    implementation(libs.data.store.preferences)
    // logging interceptor
    implementation(libs.logging.interceptor)
    // firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    // kotlin coroutines
    implementation(libs.kotlinx.coroutines.play.services)
    // appcompat
    implementation(libs.androidx.appcompat)
    // country picker
    implementation(libs.compose.country.code.picker)
    // phone number validator
    implementation(libs.libphonenumber)
    implementation(libs.androidx.cardview)
    // firebase
    implementation("com.google.firebase:firebase-messaging")
    //
    implementation("androidx.media3:media3-exoplayer:1.4.1")
    implementation("androidx.media3:media3-ui:1.4.1")
    //
    implementation(libs.androidx.compose.foundation)
}