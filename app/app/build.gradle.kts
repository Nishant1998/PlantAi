plugins {
    alias(libs.plugins.android.application)
}


android {
    namespace = "com.example.plantai"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.plantai"
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

//    packagingOptions {
//        jniLibs {
//            useLegacyPackaging = true
//        }
//        resources {
//            excludes += "**/*.tflite"
//        }
//    }
//    buildFeatures {
//        mlModelBinding = true
//    }

    androidResources{
        noCompress; "tflite"
        noCompress; "lite"
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(libs.ucrop.v2210)
    testImplementation(libs.mockwebserver)

    implementation("org.tensorflow:tensorflow-lite:+")
//    implementation("org.tensorflow:tensorflow-lite-support:2.17.1")
//    implementation("org.tensorflow:tensorflow-lite-metadata:2.17.1")
//    implementation("org.tensorflow:tensorflow-lite-task-vision:2.17.1")
//    implementation("org.tensorflow:tensorflow-lite-gpu:2.17.1")
//    implementation("org.tensorflow:tensorflow-lite-select-tf-ops:2.17.1")
//    implementation("org.tensorflow:tensorflow-lite-delegates-nnapi:2.17.1") // ✅ Add NNAPI delegate



    implementation("com.github.bumptech.glide:glide:4.15.1")
    annotationProcessor("com.github.bumptech.glide:compiler:4.15.1")
}