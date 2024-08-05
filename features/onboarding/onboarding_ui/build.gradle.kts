plugins {
				alias(libs.plugins.android.library)
				alias(libs.plugins.org.jetbrains.kotlin.android)
//				alias(libs.plugins.com.google.dagger.hilt.android)
				id("androidx.navigation.safeargs")
				id("kotlin-kapt")
}

android {
				namespace = "com.joohnq.onboarding_ui"
				compileSdk = 34

				defaultConfig {
								minSdk = 26

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
								viewBinding = true
								dataBinding = true
				}
}

kapt {
				correctErrorTypes = true
}

dependencies {
				implementation(project(":core"))
				implementation(project(":shared-resources"))
				implementation(project(":features:onboarding:onboarding_domain"))
				implementation(project(":features:onboarding:onboarding_data"))
				implementation(project(":features:user:user_ui"))
				implementation(project(":features:user:user_data"))
				implementation(project(":features:user:user_domain"))

				implementation(libs.androidx.core.ktx)
				implementation(libs.androidx.appcompat)
				implementation(libs.material)
				implementation(libs.androidx.constraintlayout)
				implementation(libs.androidx.lifecycle.runtime.ktx)
				implementation(libs.androidx.lifecycle.viewmodel.ktx)
				implementation(libs.androidx.lifecycle.livedata.ktx)
				implementation(libs.androidx.navigation.fragment.ktx)
				implementation(libs.androidx.navigation.ui.ktx)

				implementation(libs.hilt.android)
//				kapt(libs.hilt.android.compiler)

				implementation(libs.loading.button.android)

				implementation(libs.androidx.navigation.safe.args.gradle.plugin)

				implementation(platform(libs.firebase.bom))
				implementation(libs.firebase.firestore)
				implementation(libs.firebase.auth)
}