plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
    id("jacoco")
}

android {
    namespace = "in.tarun.gallery"
    compileSdk = 35

    defaultConfig {
        applicationId = "in.tarun.gallery"
        minSdk = 24
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE-notice.md"
        }
    }
}

dependencies {

    implementation(project(":components"))
    
    implementation(libs.material3)
    implementation(libs.coil.compose)
    implementation(libs.androidx.material.icons.extended)

    // ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)

    // Hilt for Dependency Injection
    implementation(libs.hilt.android)
    implementation(libs.androidx.ui.test.junit4.android)
    kapt(libs.hilt.compiler)

    implementation(libs.androidx.lifecycle.runtime.compose)

    // Unit Test Dependencies
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)

    // Android Test Dependencies (UI Tests)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(libs.androidx.compose.ui.test)
    androidTestImplementation(libs.mockk.android)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

}

kapt {
    correctErrorTypes = true
}

tasks.register<JacocoReport>("jacocoTestReport") {
    dependsOn("testDebugUnitTest")

    reports {
        xml.required.set(true)
        html.required.set(true)
    }

    val javaClasses = fileTree("${layout.buildDirectory}/intermediates/javac/debug") {
        exclude("**/Hilt*.*", "**/BuildConfig.*", "**/R.*")
    }

    classDirectories.setFrom(layout.files(javaClasses)) //
    sourceDirectories.setFrom(files("src/main/java"))
    executionData.setFrom(fileTree(layout.buildDirectory).include("**/testDebugUnitTest.exec"))
}