plugins {
				alias(libs.plugins.android.library)
				alias(libs.plugins.org.jetbrains.kotlin.android)
				alias(libs.plugins.com.google.dagger.hilt.android)
				alias(libs.plugins.ksp)
}

android {
				namespace = "com.joohnq.job.ui"
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
				implementation(project(":core:ui"))
				implementation(project(":core:domain"))
				implementation(project(":shared-resources"))
				implementation(project(":features:job:domain"))
				implementation(project(":features:job:data"))
				implementation(project(":features:user:ui"))
				implementation(project(":features:user:domain"))
				implementation(project(":features:favorite:ui"))

				implementation(libs.androidx.core.ktx)
				implementation(libs.androidx.appcompat)
				implementation(libs.material)
				implementation(libs.androidx.activity)
				implementation(libs.androidx.constraintlayout)
				implementation(libs.androidx.lifecycle.runtime.ktx)
				implementation(libs.androidx.lifecycle.viewmodel.ktx)
				implementation(libs.androidx.lifecycle.livedata.ktx)
				implementation(libs.androidx.navigation.fragment.ktx)
				implementation(libs.androidx.navigation.ui.ktx)
				implementation(libs.androidx.viewpager2)
				implementation(libs.glide)
				implementation(libs.loading.button.android)
				implementation(libs.hilt.android)
				ksp(libs.hilt.android.compiler)

				testImplementation(libs.bundles.test)
}
