// build.gradle
plugins {
    id 'com.android.application'
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdkVersion(33)
    defaultConfig {
        applicationId = "com.example.bankapp"
        minSdkVersion(21)
        targetSdkVersion(33)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            minifyEnabled(false)
            proguardFiles(getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro')
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    packagingOptions {
        resources.excludes += "/META-INF/{NOTICE,LICENSE,LICENSE.txt,NOTICE.txt,ASL2.0}"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-ktx:1.8.0")
    implementation("androidx.fragment:fragment-ktx:1.6.1")
    implementation("com.zaxxer:HikariCP:5.0.1") // HikariCP for database connection pooling
    implementation("org.apache.logging.log4j:log4j-api:2.20.0")  // Log4j for logging
    implementation("org.apache.logging.log4j:log4j-core:2.20.0")  // Log4j core functionality
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
