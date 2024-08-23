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
				implementation(project(":features:profile"))
				implementation(project(":features:show_all:show_all_ui"))
				kapt(libs.hilt.android.compiler)

				implementation(libs.androidx.navigation.fragment.ktx)
				implementation(libs.androidx.navigation.ui.ktx)

				implementation(libs.circleimageview)
}
