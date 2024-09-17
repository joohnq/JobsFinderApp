plugins {
				alias(libs.plugins.android.library)
				alias(libs.plugins.org.jetbrains.kotlin.android)
				alias(libs.plugins.com.google.dagger.hilt.android)
				id("kotlin-kapt")
}

android {
				namespace = "com.joohnq.features"
				compileSdk = libs.versions.compileSdk.get().toInt()

				defaultConfig {
								minSdk = libs.versions.minSdk.get().toInt()

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
								sourceCompatibility = JavaVersion.VERSION_17
								targetCompatibility = JavaVersion.VERSION_17
				}
				kotlinOptions {
								jvmTarget = JavaVersion.VERSION_17.toString()
				}
				buildFeatures {
								viewBinding = true
								dataBinding = true
				}
}

dependencies {
				implementation(project(":shared-resources"))
				implementation(project(":core"))
				implementation(project(":features:home"))
				implementation(project(":features:search"))
				implementation(project(":features:favorite:favorite_ui"))
				implementation(project(":features:job:job_ui"))
				implementation(project(":features:onboarding:onboarding_ui"))
				implementation(project(":features:user:user_domain"))
				implementation(project(":features:user:user_ui"))

				implementation(libs.bundles.base)
				implementation(libs.bundles.navigation)
				implementation(libs.hilt.android)
				implementation(libs.android.loading.dots)

				kapt(libs.hilt.android.compiler)
}
