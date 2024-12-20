plugins {
				alias(libs.plugins.android.library)
				alias(libs.plugins.org.jetbrains.kotlin.android)
}

android {
				namespace = "com.joohnq.user_data"
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
				implementation(project(":features:user:user_domain"))
				implementation(libs.androidx.core.ktx)
				implementation(libs.hilt.android)
				implementation(platform(libs.firebase.bom))
				implementation(libs.bundles.firebase)

				testImplementation(libs.bundles.test)
				implementation(libs.bundles.retrofit)
				testImplementation(project(":core_test"))
}