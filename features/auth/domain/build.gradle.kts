plugins {
				alias(libs.plugins.android.library)
				alias(libs.plugins.org.jetbrains.kotlin.android)
				id("com.google.devtools.ksp")
}

android {
				namespace = "com.joohnq.domain"
				compileSdk = 35

				defaultConfig {
								minSdk = 24

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
								sourceCompatibility = JavaVersion.VERSION_11
								targetCompatibility = JavaVersion.VERSION_11
				}
				kotlinOptions {
								jvmTarget = "11"
				}
}