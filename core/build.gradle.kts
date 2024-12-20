plugins {
				alias(libs.plugins.android.library)
				alias(libs.plugins.org.jetbrains.kotlin.android)
				alias(libs.plugins.com.google.dagger.hilt.android)
				id("com.google.devtools.ksp")
				kotlin("kapt")
}

android {
				namespace = "com.joohnq.core"
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
								dataBinding = true
								viewBinding = true
				}
}

dependencies {
				implementation(project(":features:job:job_domain"))
				implementation(project(":features:show_all:show_all_domain"))
				implementation(project(":shared-resources"))

				implementation(libs.material)

				implementation(libs.loading.button.android)

				implementation(libs.hilt.android)
				implementation(libs.core.ktx)
				implementation(libs.androidx.runner)
				ksp(libs.hilt.android.compiler)

				implementation(platform(libs.firebase.bom))
				implementation(libs.firebase.firestore)
				implementation(libs.firebase.auth)
				implementation(libs.firebase.storage)

				implementation(libs.bundles.retrofit)
}