plugins {
				alias(libs.plugins.android.library)
				alias(libs.plugins.org.jetbrains.kotlin.android)
				alias(libs.plugins.com.google.dagger.hilt.android)
				id("com.google.devtools.ksp")
}

android {
				namespace = "com.joohhq.loading"
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
				buildFeatures {
								viewBinding = true
								dataBinding = true
				}
}

dependencies {
				implementation(project(":core"))
				implementation(project(":features:user:user_ui"))
				implementation(project(":features:user:user_domain"))
				implementation(project(":features:onboarding:onboarding_ui"))
				implementation(project(":features:main"))

				implementation(libs.bundles.base)
				implementation(libs.bundles.navigation)
				implementation(libs.hilt.android)
				implementation(libs.android.loading.dots)

				ksp(libs.hilt.android.compiler)
}