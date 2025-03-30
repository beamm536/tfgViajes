plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.appclass.myapplication"
    compileSdk = 35 //34

    defaultConfig {
        applicationId = "com.appclass.myapplication"
        minSdk = 30
        targetSdk = 35 //34
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
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.espresso.core)
    implementation(libs.firebase.auth.ktx) //libreria para auth
    implementation(libs.firebase.firestore.ktx) //firestore
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //firebase
    implementation(platform("com.google.firebase:firebase-bom:33.8.0"))

    //dependencias para las fuentes
    //implementation (libs.material3)
    //
    //implementation(libs.androidx.ui.text.google.fonts)

    //implementation(libs.accompanist.blur)
    implementation(kotlin("script-runtime"))

    //RETROFIT
    //implementation("com.squareup.retrofit2:retrofit:2.11.0")
        //retrofit - para la conversion del tipo de json
         //implementation("com.squareup.retrofit2:converter-moshi:2.11.0") //converter --> serialization JSON ----- MOSHI
         //implementation("com.squareup.retrofit2:converter-gson:2.11.0") //GSON
    implementation ("com.squareup.retrofit2:retrofit:2.11.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.11.0")


    //MAPBOX -- 11.10.3 (last version)
    implementation ("com.mapbox.maps:android:10.15.0")

    //depuracion de solicitudes
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
}