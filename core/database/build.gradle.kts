plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)

    // room + di
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "ru.ttb220.database"
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
}

kapt {
    correctErrorTypes = true

    // room export schema location
    arguments {
        arg("room.schemaLocation", "$projectDir/schemas")
    }
}

dependencies {
    api(project(":core:model"))

    // room
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)
    // solely for sync testing
    debugApi(libs.androidx.room.ktx)
    debugApi(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)

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