plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "org.castre.fytro.designsystem"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    api(platform(libs.androidx.compose.bom))

    implementation(libs.material)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    api(libs.androidx.activity.compose)
    api(libs.androidx.animation)
    api(libs.androidx.animation.graphics)
    api(libs.androidx.foundation)
    api(libs.androidx.material3)
    api(libs.androidx.navigation)
    api(libs.androidx.ui)
    api(libs.androidx.ui.graphics)
    debugApi(libs.androidx.ui.tooling)
    api(libs.androidx.ui.tooling.preview)
}
