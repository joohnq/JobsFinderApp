plugins {
				alias(libs.plugins.android.library)
				alias(libs.plugins.org.jetbrains.kotlin.android)
				alias(libs.plugins.com.google.dagger.hilt.android)
				alias(libs.plugins.ksp)
}

android {
				namespace = "com.joohnq.user.ui"
				compileSdk = libs.versions.compile.sdk.get().toInt()

				defaultConfig {
								minSdk = libs.versions.min.sdk.get().toInt()

								testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
				}

				buildTypes {
								release {
												isMinifyEnabled = false
								}
				}
				compileOptions {
								sourceCompatibility = JavaVersion.VERSION_17
								targetCompatibility = JavaVersion.VERSION_17
				}
				kotlinOptions {
								jvmTarget = JavaVersion.VERSION_17.toString()
				}
				buildFeatures{
								viewBinding = true
				}
}

dependencies {
				implementation(project(":features:auth:domain"))
				implementation(project(":core:ui"))
				implementation(project(":core:domain"))
				implementation(project(":shared-resources"))
				implementation(project(":features:user:data"))
				implementation(project(":features:user:domain"))

				implementation(libs.bundles.base)
				implementation(libs.bundles.navigation)

				implementation(libs.glide)
				implementation(libs.circleimageview)
				implementation(libs.loading.button.android)

				implementation(libs.hilt.android)
				ksp(libs.hilt.android.compiler)

				testImplementation(libs.bundles.test)
}
