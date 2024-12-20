plugins {
				alias(libs.plugins.android.library)
				alias(libs.plugins.org.jetbrains.kotlin.android)
				alias(libs.plugins.com.google.dagger.hilt.android)
				id("com.google.devtools.ksp")
}

android {
				namespace = "com.joohnq.onboarding_data"
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
				packaging {
								resources {
												excludes += "META-INF/*"
								}
				}
}

dependencies {
				implementation(project(":core"))
				implementation(project(":features:user:user_domain"))

				implementation(platform(libs.firebase.bom))
				implementation(libs.firebase.auth)

				implementation(libs.hilt.android)
				ksp(libs.hilt.android.compiler)

				implementation(libs.google.id)
				implementation(libs.play.services.auth)

				implementation(libs.bundles.retrofit)

				testImplementation(libs.bundles.test)

				implementation("androidx.datastore:datastore-preferences:1.1.1")
}