plugins {
				alias(libs.plugins.android.library)
				alias(libs.plugins.org.jetbrains.kotlin.android)
				alias(libs.plugins.ksp)
}

android {
				namespace = "com.joohnq.auth.data"
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
				implementation(project(":features:auth:domain"))
				implementation(project(":core:data"))
				implementation(libs.bundles.retrofit)
				implementation(libs.google.id)
				implementation("androidx.datastore:datastore-preferences:1.1.1")

				implementation(libs.hilt.android)
				ksp(libs.hilt.android.compiler)
}