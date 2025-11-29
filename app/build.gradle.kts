plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "lb.edu.ul.khayme_w_nar"
    compileSdk = 35

    defaultConfig {
        applicationId = "lb.edu.ul.khayme_w_nar"
        minSdk = 35
        targetSdk = 35
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // Room dependencies




    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler) // For Java projects
    // kapt(libs.room.compiler) // For Kotlin projects

    // Optional: Room Kotlin extensions (if using Kotlin)
    // implementation(libs.room.ktx)

    // Optional: Room testing support
    androidTestImplementation(libs.room.testing)

    // RecyclerView dependency
    implementation(libs.recyclerview) // Add this line

        // Other dependencies
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}