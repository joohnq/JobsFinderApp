plugins {
				alias(libs.plugins.android.library)
				alias(libs.plugins.org.jetbrains.kotlin.android)
				alias(libs.plugins.com.google.dagger.hilt.android)
				id("com.google.devtools.ksp")
				kotlin("kapt")
}

android {
				namespace = "com.joohnq.user.user_ui"
				compileSdk = libs.versions.compile.sdk.get().toInt()

				defaultConfig {
								minSdk = libs.versions.min.sdk.get().toInt()

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
				implementation(project(":shared-resources"))
				implementation(project(":features:user:user_data"))
				implementation(project(":features:user:user_domain"))
				implementation(project(":core"))
				
				implementation(libs.androidx.core.ktx)
				implementation(libs.androidx.appcompat)
				implementation(libs.material)
				implementation(libs.androidx.constraintlayout)
				implementation(libs.androidx.lifecycle.runtime.ktx)
				implementation(libs.androidx.lifecycle.viewmodel.ktx)
				implementation(libs.androidx.lifecycle.livedata.ktx)
				implementation(libs.hilt.android)
				ksp(libs.hilt.android.compiler)
				implementation(libs.androidx.navigation.fragment.ktx)
				implementation(libs.androidx.navigation.ui.ktx)
				implementation(libs.glide)

				testImplementation(libs.bundles.test)
}
