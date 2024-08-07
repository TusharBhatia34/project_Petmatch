plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    
    //firebase
    alias(libs.plugins.googleServices)

    //DaggerHilt
    kotlin("kapt")
    alias(libs.plugins.daggerHilt)

   alias(libs.plugins.kotlinxSerialization)

}

android {
    namespace = "com.example.petadoptionapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.petadoptionapp"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    //room
    implementation (libs.androidx.room.runtime)
    implementation (libs.androidx.room.ktx)
    kapt (libs.androidx.room.compiler)

    //firebase
    implementation(platform(libs.firebase.bom))
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-auth")

    //navigation
    implementation(libs.androidx.navigation.compose)

    //coil
    implementation(libs.coil.compose)


    //to create viewmodel instance
    implementation (libs.androidx.lifecycle.viewmodel.compose)

    implementation(libs.kotlinx.serialization.json)
    //Dagger Hilt
    implementation(libs.hilt.android)
    kapt(libs.dagger.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    //for  kotlin coroutine (added)
    implementation(libs.kotlinx.coroutines.android)

   implementation (libs.androidx.material.icons.extended)
    implementation(libs.firebase.storage)

    //google maps
    implementation(libs.maps.compose)
    implementation(libs.play.services.location)

    //calendar picker
    // Implementing the `core` module is mandatory for using other use cases.
    implementation("com.maxkeppeler.sheets-compose-dialogs:core:1.0.0")
    implementation("com.maxkeppeler.sheets-compose-dialogs:calendar:1.0.0")

    //datastore
    implementation(libs.androidx.datastore.preferences)

    //accompanist
    implementation(libs.accompanist.permissions)


}
//DaggerHilt
kapt {
    correctErrorTypes = true
}
