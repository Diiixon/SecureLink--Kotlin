plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp) // <-- Asegúrate de que esté así
}

android {
    namespace = "com.example.securelink"
    compileSdk = 36


    defaultConfig {
        applicationId = "com.example.securelink"
        minSdk = 28
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    //sQLITE
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:${room_version}")
    ksp("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:${room_version}")


    implementation("androidx.datastore:datastore-preferences:1.1.1")
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation("androidx.compose.material:material-icons-extended:1.6.8") // <-- Iconos extendidos
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.1")
    // LiveData/StateFlow observation
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.1")
    implementation("androidx.navigation:navigation-compose:2.9.5")
    // Para escanear códigos QR
    implementation("com.journeyapps:zxing-android-embedded:4.3.0")
    // Vico para gráficos (Core y Compose)
    implementation("com.patrykandpatrick.vico:compose-m3:1.14.0") // O vico:compose si no usas M3
    implementation("com.patrykandpatrick.vico:core:1.14.0")
    // Coil para cargar imágenes
    implementation("io.coil-kt:coil-compose:2.6.0")

}
