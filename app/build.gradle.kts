import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    if (File("app/google-services.json").exists()) {
        println("Enable gms in app plugins")
        alias(libs.plugins.gms)
        alias(libs.plugins.crashlytics)
    }
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("crouter-plugin")
}

android {
    namespace = "me.ckn.music"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "me.ckn.music"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()

        multiDexEnabled = true

        ndk {
            abiFilters.apply {
                add("armeabi-v7a")  // ARM 32位 - 真机支持
                add("arm64-v8a")    // ARM 64位 - 真机支持
                add("x86")          // x86 32位 - 模拟器支持
                add("x86_64")       // x86 64位 - 模拟器支持
            }
        }

        applicationVariants.all {
            outputs.all {
                if (this is com.android.build.gradle.internal.api.ApkVariantOutputImpl) {
                    this.outputFileName = "whisperplay-$versionName.apk"
                }
            }
        }
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    signingConfigs {
        register("release") {
            enableV1Signing = true
            enableV2Signing = true
            storeFile = file("ckn.keystore")
            storePassword = getLocalValue("STORE_PASSWORD")
            keyAlias = getLocalValue("KEY_ALIAS")
            keyPassword = getLocalValue("KEY_PASSWORD")
        }
    }

    buildTypes {
        debug {
            buildConfigField("boolean", "DEBUG", "true")
            // 调试版本支持所有架构（包括模拟器）
            ndk {
                abiFilters.clear()
                abiFilters.addAll(listOf("armeabi-v7a", "arm64-v8a", "x86", "x86_64"))
            }
        }

        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
            buildConfigField("boolean", "DEBUG", "false")
            // 发布版本只支持真机架构（减小APK大小）
            ndk {
                abiFilters.clear()
                abiFilters.addAll(listOf("armeabi-v7a", "arm64-v8a"))
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.valueOf(libs.versions.java.get())
        targetCompatibility = JavaVersion.valueOf(libs.versions.java.get())
    }

    kotlinOptions {
        jvmTarget = JavaVersion.valueOf(libs.versions.java.get()).toString()
        // 抑制已过时API警告和其他编译警告
        freeCompilerArgs += listOf(
            "-Xsuppress-deprecated-jvm-target-warning",
            "-opt-in=kotlin.RequiresOptIn"
        )
        // 抑制所有警告
        allWarningsAsErrors = false
        suppressWarnings = true
    }

    // 针对性抑制特定Java编译警告
    tasks.withType<JavaCompile> {
        options.compilerArgs.addAll(listOf(
            "-Xlint:-deprecation",    // 抑制过时API警告
            "-Xlint:-unchecked",      // 抑制未检查类型警告
            "-Xlint:-processing"      // 抑制注解处理器警告
        ))
    }
}

fun getLocalValue(key: String): String {
    return getLocalValue(key, false)
}

fun getLocalValue(key: String, quot: Boolean): String {
    val properties = Properties()
    properties.load(project.rootProject.file("local.properties").inputStream())
    var value = if (properties.containsKey(key)) {
        properties[key].toString()
    } else {
        ""
    }
    if (quot) {
        value = "\"" + value + "\""
    }
    return value
}

kapt {
    // For hilt: Allow references to generated code
    correctErrorTypes = true
    // 抑制注解处理器警告
    arguments {
        arg("dagger.fastInit", "enabled")
        arg("dagger.hilt.android.internal.disableAndroidSuperclassValidation", "true")
        arg("dagger.hilt.android.internal.projectType", "APP")
        arg("dagger.hilt.internal.useAggregatingRootProcessor", "false")
    }
    // 针对性抑制kapt相关警告
    javacOptions {
        option("-Xlint:-deprecation")
        option("-Xlint:-unchecked")
        option("-Xlint:-processing")
    }
}

ksp {
    arg("moduleName", project.name)
    // crouter 默认 scheme
    arg("defaultScheme", "app")
    // crouter 默认 host
    arg("defaultHost", "whisperplay")
    arg("room.schemaLocation", "$projectDir/schemas")
    arg("crouter.registerPackage", "me.ckn.music")
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
    implementation(libs.material)
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation(libs.media3.exoplayer)
    implementation(libs.media3.datasource.okhttp)
    implementation(libs.media3.session)
    implementation(libs.media3.ui)
    implementation(libs.j2objc.annotations)
    implementation(libs.preference)
    implementation(libs.flexbox)

    ksp(libs.room.compiler)
    implementation(libs.room)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt)

    if (File("${project.projectDir}/google-services.json").exists()) {
        println("Enable gms in app dependencies")
        implementation(libs.crashlytics)
        implementation(libs.analytics)
    }

    implementation(libs.common)
    // implementation(project(":common"))
    ksp(libs.crouter.processor)
    implementation(libs.crouter.api)
    implementation(libs.lrcview)

    implementation(libs.loggingInterceptor)
    implementation(libs.zbar)
    implementation(libs.blurry)
    implementation(libs.banner)
    // 移除 error_prone_annotations 以解决 j2objc ReflectionSupport 警告
    // implementation(libs.errorprone.annotations)
}
