import java.util.Properties

plugins {
				alias(libs.plugins.android.library)
				alias(libs.plugins.org.jetbrains.kotlin.android)
				alias(libs.plugins.com.google.dagger.hilt.android)
				kotlin("plugin.serialization") version "2.0.10"
				id("kotlin-kapt")
}

android {
				namespace = "com.joohnq.job_data"
				compileSdk = libs.versions.compileSdk.get().toInt()
				val properties = Properties()
				properties.load(project.rootProject.file("local.properties").inputStream())

				defaultConfig {
								minSdk = libs.versions.minSdk.get().toInt()

								buildConfigField("String", "PROJECT_URL", "\"${properties.getProperty("PROJECT_URL")}\"")
								buildConfigField("String", "API_KEY", "\"${properties.getProperty("API_KEY")}\"")

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
								sourceCompatibility = JavaVersion.VERSION_17
								targetCompatibility = JavaVersion.VERSION_17
				}
				kotlinOptions {
								jvmTarget = JavaVersion.VERSION_17.toString()
				}
				buildFeatures {
								buildConfig = true
				}
}

dependencies {
				implementation(project(":core"))
				implementation(project(":features:job:job_domain"))

				implementation(libs.androidx.core.ktx)
				implementation(libs.androidx.appcompat)
				implementation(libs.material)
				implementation(libs.androidx.lifecycle.runtime.ktx)
				implementation(libs.androidx.lifecycle.viewmodel.ktx)
				implementation(libs.androidx.lifecycle.livedata.ktx)

				implementation(libs.hilt.android)
				kapt(libs.hilt.android.compiler)

				implementation(libs.kotlinx.serialization.json)

				implementation(platform(libs.supabase.bom))
				implementation(libs.supabase.postgrest.kt)
				implementation(libs.ktor.client.android)

				implementation(libs.retrofit)
				implementation(libs.converter.gson)
}