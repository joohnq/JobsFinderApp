plugins {
				alias(libs.plugins.android.library)
				alias(libs.plugins.org.jetbrains.kotlin.android)
				alias(libs.plugins.com.google.dagger.hilt.android)
				alias(libs.plugins.ksp)
}

android {
				namespace = "com.joohnq.favorite.ui"
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
				implementation(project(":core:domain"))
				implementation(project(":core:ui"))
				implementation(project(":shared-resources"))
				implementation(project(":features:job:domain"))
				implementation(project(":features:job:data"))
				implementation(project(":features:favorite:data"))

				implementation(libs.bundles.base)
				implementation(libs.bundles.navigation)

				implementation(libs.hilt.android)
				ksp(libs.hilt.android.compiler)

				testImplementation(libs.bundles.test)
}