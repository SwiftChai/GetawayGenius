plugins {
    alias(libs.plugins.android.application)
    //Dependency for Google services Gradle plugin
    id("com.google.gms.google-services")
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.mytravelapplication"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.mytravelapplication"
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
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    implementation(libs.room.runtime)
    implementation(libs.core.ktx)
    annotationProcessor(libs.room.compiler)
    androidTestImplementation(libs.room.testing)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    //imports Firebase BOM
    implementation(platform("com.google.firebase:firebase-bom:34.10.0"))
    //Firebase Auth dependency
    implementation("com.google.firebase:firebase-auth")
}