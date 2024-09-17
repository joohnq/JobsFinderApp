plugins {
				alias(libs.plugins.android.library)
				alias(libs.plugins.org.jetbrains.kotlin.android)
				alias(libs.plugins.com.google.dagger.hilt.android)
				id("androidx.navigation.safeargs.kotlin")
				id("kotlin-kapt")
}

android {
				namespace = "com.joohnq.onboarding_ui"
				compileSdk = libs.versions.compileSdk.get().toInt()

				defaultConfig {
								minSdk = libs.versions.minSdk.get().toInt()

								testInstrumentationRunner = "com.joohnq.onboarding_ui.CustomTestRunner"
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
				implementation(libs.androidx.junit.ktx)
				implementation(libs.androidx.activity)
				kapt(libs.hilt.android.compiler)

				implementation(libs.loading.button.android)

//				implementation(libs.androidx.navigation.safe.args.gradle.plugin)

				implementation(platform(libs.firebase.bom))
				implementation(libs.firebase.firestore)
				implementation(libs.firebase.auth)

				testImplementation(libs.truth)
				testImplementation(libs.androidx.core.testing)
				testImplementation(libs.kotlinx.coroutines.test)
				testImplementation(libs.junit)
				testImplementation(libs.mockk.android)

				androidTestImplementation(project(":core"))
				androidTestImplementation(libs.androidx.navigation.testing)
				androidTestImplementation(libs.androidx.espresso.core)
				androidTestImplementation(libs.hilt.android.testing)
				androidTestImplementation(libs.androidx.core.testing)
				androidTestImplementation(libs.mockito.android)
				androidTestImplementation(libs.mockk.android)
				kaptAndroidTest(libs.hilt.android.compiler)
				androidTestImplementation(libs.androidx.core)
				implementation (libs.androidx.core)
//				androidTestImplementation(libs.kotlinx.coroutines.core)
//				androidTestImplementation (libs.kotlinx.coroutines.test)
//				androidTestImplementation(libs.truth)
}