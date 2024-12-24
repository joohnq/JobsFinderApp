plugins {
				alias(libs.plugins.com.android.application)
				alias(libs.plugins.org.jetbrains.kotlin.android)
				alias(libs.plugins.com.google.dagger.hilt.android)
				alias(libs.plugins.com.google.gms.google.services)
				alias(libs.plugins.ksp)
}

android {
				namespace = "com.joohnq.jobsfinderapp"
				compileSdk = libs.versions.compile.sdk.get().toInt()
				defaultConfig {
								applicationId = "com.joohnq.jobsfinderapp"
								minSdk = libs.versions.min.sdk.get().toInt()
								targetSdk = libs.versions.target.sdk.get().toInt()
								versionCode = libs.versions.version.code.get().toInt()
								versionName = libs.versions.version.name.get()

								testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
				}

				buildTypes {
								release {
												isMinifyEnabled = false
								}
				}
				buildFeatures {
								viewBinding = true
				}
				compileOptions {
								sourceCompatibility = JavaVersion.VERSION_17
								targetCompatibility = JavaVersion.VERSION_17
				}
				kotlinOptions {
								jvmTarget = JavaVersion.VERSION_17.toString()
				}
}

dependencies {
				implementation(project(":shared-resources"))
				implementation(project(":features:main"))
				implementation(project(":features:job:ui"))
				implementation(project(":features:onboarding:ui"))
				implementation(project(":features:user:ui"))

				implementation(libs.androidx.navigation.fragment.ktx)
				implementation(libs.androidx.navigation.ui.ktx)

				implementation(libs.hilt.android)
				ksp(libs.hilt.android.compiler)

				implementation(libs.firebase.analytics)

				debugImplementation(libs.androidx.fragment.testing)

}
