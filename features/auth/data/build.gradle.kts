plugins {
				alias(libs.plugins.android.library)
				alias(libs.plugins.org.jetbrains.kotlin.android)
				alias(libs.plugins.ksp)
}

android {
				namespace = "com.joohnq.data"
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
}

dependencies {
				implementation(project(":core"))
				implementation(project(":features:auth:domain"))
				implementation(libs.bundles.retrofit)
				implementation(libs.google.id)
				implementation("androidx.datastore:datastore-preferences:1.1.1")

				implementation(libs.hilt.android)
				ksp(libs.hilt.android.compiler)
}