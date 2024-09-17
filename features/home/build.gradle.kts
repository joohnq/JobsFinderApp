plugins {
				alias(libs.plugins.android.library)
				alias(libs.plugins.org.jetbrains.kotlin.android)
				alias(libs.plugins.com.google.dagger.hilt.android)
				id("kotlin-kapt")
}

android {
				namespace = "com.joohnq.home"
				compileSdk = libs.versions.compileSdk.get().toInt()

				defaultConfig {
								minSdk = libs.versions.minSdk.get().toInt()

								testInstrumentationRunner = "com.joohnq.home.CustomTestRunner"
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
								dataBinding = true
								viewBinding = true
				}
				packaging {
								resources {
												excludes += "META-INF/*.md"
								}
				}
}

kapt {
				correctErrorTypes = true
}

dependencies {
				implementation(project(":core"))
				implementation(project(":shared-resources"))
				implementation(project(":features:job:job_data"))
				implementation(project(":features:job:job_ui"))
				implementation(project(":features:job:job_domain"))
				implementation(project(":features:user:user_ui"))
				implementation(project(":features:user:user_domain"))
				implementation(project(":features:favorite:favorite_ui"))
				implementation(project(":features:show_all:show_all_domain"))
				implementation(project(":features:profile"))
				implementation(project(":features:show_all:show_all_ui"))

				implementation(libs.androidx.swiperefreshlayout)
				implementation(libs.androidx.core.ktx)
				implementation(libs.androidx.appcompat)
				implementation(libs.material)
				implementation(libs.androidx.constraintlayout)
				implementation(libs.androidx.lifecycle.runtime.ktx)
				implementation(libs.androidx.lifecycle.viewmodel.ktx)
				implementation(libs.androidx.lifecycle.livedata.ktx)
				implementation(libs.androidx.navigation.fragment.ktx)
				implementation(libs.androidx.navigation.ui.ktx)
				implementation(libs.androidx.activity)
				implementation(libs.androidx.material3)
				implementation(libs.hilt.android)
				implementation(libs.androidx.junit.ktx)
				implementation(libs.core)
				kapt(libs.hilt.android.compiler)

				implementation(libs.androidx.navigation.fragment.ktx)
				implementation(libs.androidx.navigation.ui.ktx)

				implementation(libs.circleimageview)

				testImplementation(project(":features:user:user_data"))
				testImplementation(project(":features:favorite:favorite_data"))
				testImplementation(libs.bundles.test)

				androidTestImplementation(project(":app"))
				androidTestImplementation(project(":core"))
				debugImplementation(project(":core"))
				androidTestImplementation(libs.hilt.android.testing)
				androidTestImplementation(libs.junit)
				androidTestImplementation(libs.androidx.junit.ktx)
				androidTestImplementation(libs.kotlinx.coroutines.test)
				androidTestImplementation(libs.androidx.core.testing)
				androidTestImplementation(libs.androidx.core)
				androidTestImplementation(libs.mockito.android)
				androidTestImplementation(libs.mockk.android)
				androidTestImplementation(libs.truth)
				androidTestImplementation(libs.androidx.fragment.testing)
				androidTestImplementation(libs.androidx.runner)
				androidTestImplementation(libs.androidx.rules)
				androidTestImplementation(libs.androidx.espresso.core)
//				androidTestImplementation(libs.androidx.espresso.contrib)
//				androidTestImplementation(libs.androidx.espresso.intents)
}
