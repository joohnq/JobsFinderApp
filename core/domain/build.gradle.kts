plugins {
				alias(libs.plugins.android.library)
				alias(libs.plugins.org.jetbrains.kotlin.android)
}

android {
				namespace = "com.joohnq.core.domain"
				compileSdk = libs.versions.compile.sdk.get().toInt()

				defaultConfig {
								minSdk = libs.versions.min.sdk.get().toInt()

								testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
				}

				buildTypes {
								release {
												isMinifyEnabled = false
								}
								compileOptions {
												sourceCompatibility = JavaVersion.VERSION_17
												targetCompatibility = JavaVersion.VERSION_17
								}
								kotlinOptions {
												jvmTarget = JavaVersion.VERSION_17.toString()
								}
				}
}