plugins {
				alias(libs.plugins.android.library)
				alias(libs.plugins.org.jetbrains.kotlin.android)
				alias(libs.plugins.com.google.dagger.hilt.android)
				id("kotlin-kapt")
}

android {
				namespace = "com.joohnq.onboarding_data"
				compileSdk = libs.versions.compileSdk.get().toInt()

				defaultConfig {
								minSdk = libs.versions.minSdk.get().toInt()

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
				packaging {
								resources {
												excludes += "META-INF/*"
								}
				}
}

kapt {
				correctErrorTypes = true
}

dependencies {
				implementation(project(":core"))
				implementation(project(":features:user:user_domain"))
				implementation(project(":features:onboarding:onboarding_domain"))

				implementation(platform(libs.firebase.bom))
				implementation(libs.firebase.auth)

				implementation(libs.hilt.android)
				kapt(libs.hilt.android.compiler)

				implementation(libs.google.id)
				implementation(libs.play.services.auth)

				testImplementation(libs.truth)
				testImplementation(libs.androidx.core.testing)
				testImplementation(libs.kotlinx.coroutines.test)
				testImplementation(libs.junit)
				testImplementation(libs.robolectric)
				testImplementation(libs.mockk)
				testImplementation(libs.mockk.android)
}