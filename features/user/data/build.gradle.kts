plugins {
				alias(libs.plugins.android.library)
				alias(libs.plugins.org.jetbrains.kotlin.android)
}

android {
				namespace = "com.joohnq.user.data"
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
}

dependencies {
				implementation(project(":core:data"))
				implementation(project(":core:domain"))
				testImplementation(project(":core-test"))
				implementation(project(":features:user:domain"))
				implementation(libs.androidx.core.ktx)
				implementation(libs.hilt.android)

				testImplementation(libs.bundles.test)
				implementation(libs.bundles.retrofit)
}