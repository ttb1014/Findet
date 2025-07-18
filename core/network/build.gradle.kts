plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)

    // di
    alias(libs.plugins.kotlin.kapt)

    // kotlinx.serialization
    alias(libs.plugins.kotlin.serialization)

    // secrets
    alias(libs.plugins.secrets)
}

android {
    namespace = "ru.ttb220.network"
    compileSdk = 36

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
        buildConfig = true
    }
}

kapt {
    correctErrorTypes = true
}

secrets {
    defaultPropertiesFileName = "local.properties"
}

dependencies {
    api(project(":core:model"))

    // retrofit +json + kotlinx.serialization impl
    api(libs.retrofit)
    api(libs.kotlinx.serialization.json)
    api(libs.retrofit.kotlin.serialization)
    api(libs.okhttp.logging)

    // di
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}