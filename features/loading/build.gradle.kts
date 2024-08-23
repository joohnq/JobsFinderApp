plugins {
				alias(libs.plugins.android.library)
				alias(libs.plugins.org.jetbrains.kotlin.android)
				alias(libs.plugins.com.google.dagger.hilt.android)
				id("kotlin-kapt")
}

android {
				namespace = "com.joohnq.loading"
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
				buildFeatures{
								dataBinding = true
								viewBinding = true
				}
}

dependencies {
				implementation(project(":core"))
				implementation(project(":features:onboarding:onboarding_ui"))
				implementation(project(":features:main"))
				implementation(project(":features:user:user_domain"))
				implementation(project(":features:user:user_ui"))

				implementation(libs.androidx.core.ktx)
				implementation(libs.androidx.appcompat)
				implementation(libs.material)
				implementation(libs.androidx.constraintlayout)

				implementation(libs.hilt.android)
				kapt(libs.hilt.android.compiler)

				implementation(libs.android.loading.dots)
}