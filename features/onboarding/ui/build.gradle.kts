plugins {
				alias(libs.plugins.android.library)
				alias(libs.plugins.org.jetbrains.kotlin.android)
				alias(libs.plugins.com.google.dagger.hilt.android)
				id("androidx.navigation.safeargs.kotlin")
				alias(libs.plugins.ksp)
}

android {
				namespace = "com.joohnq.onboarding.ui"
				compileSdk = libs.versions.compile.sdk.get().toInt()

				defaultConfig {
								minSdk = libs.versions.min.sdk.get().toInt()

								testInstrumentationRunner = "com.joohnq.ui.CustomTestRunner"
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
				buildFeatures {
								viewBinding = true
				}

				packaging{
								resources{
												excludes.addAll(
																listOf(
																				"META-INF/*"
																)
												)
								}
				}
}

dependencies {
				implementation(project(":shared-resources"))
				implementation(project(":core:ui"))
				implementation(project(":core:domain"))
				implementation(project(":features:user:ui"))
				implementation(project(":features:user:data"))
				implementation(project(":features:user:domain"))
				implementation(project(":features:auth:data"))
				implementation(project(":features:auth:domain"))

				implementation(libs.bundles.base)
				implementation(libs.bundles.navigation)

				implementation(libs.hilt.android)
				ksp(libs.hilt.android.compiler)

				implementation(libs.loading.button.android)

				testImplementation(libs.bundles.test)

				androidTestImplementation(libs.androidx.navigation.testing)
				androidTestImplementation(libs.androidx.espresso.core)
				androidTestImplementation(libs.hilt.android.testing)
				androidTestImplementation(libs.androidx.core.testing)
				androidTestImplementation(libs.mockito.android)
				androidTestImplementation(libs.mockk.android)
				kspAndroidTest(libs.hilt.android.compiler)
				androidTestImplementation(libs.androidx.core)
}