plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    // di
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "ru.ttb220.findet"
    compileSdk = 36

    defaultConfig {
        applicationId = "ru.ttb220.findet"
        minSdk = 24
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
    api(project(":core:sync"))
    api(project(":core:splash"))

    api(project(":feature:account"))
    api(project(":feature:bottomsheet"))
    api(project(":feature:category"))
    api(project(":feature:expense"))
    api(project(":feature:income"))
    api(project(":feature:setting"))
    api(project(":feature:pin"))

    // di
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)

    // splash
    implementation(libs.androidx.core.splashscreen)

    // compose + nav
    implementation(libs.androidx.lyfecycle.runtime.compose)
    implementation(libs.androidx.navigation.compose)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.test.manifest)

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
}