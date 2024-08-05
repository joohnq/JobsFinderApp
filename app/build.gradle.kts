plugins {
				alias(libs.plugins.com.android.application)
				alias(libs.plugins.org.jetbrains.kotlin.android)
				id("com.google.gms.google-services")
				alias(libs.plugins.com.google.dagger.hilt.android)
				id("kotlin-kapt")
}

android {
				namespace = "com.joohnq.jobsfinderapp"
				compileSdk = 34

				defaultConfig {
								applicationId = "com.joohnq.jobsfinderapp"
								minSdk = 26
								targetSdk = 34
								versionCode = 1
								versionName = "1.0"

								testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
												excludes += "mozilla/public-suffix-list.txt"
												excludes += "google/protobuf/*.proto"
												excludes += "xsd/catalog.xml"
												excludes += "**/*.kotlin_builtins"
								}
				}
				configurations {
								all {
//												exclude(module = "kotlin-compiler-embeddable")
												exclude(module = "kotlin-gradle-plugin")
												exclude(module = "kxml2")
												exclude(module = "xmlpull")
												exclude(module = "xml-apis")
												exclude(module = "kotlin-gradle-plugin-api")
												exclude(module = "proto-google-common-protos")
												exclude(module = "protolite-well-known-types")
												exclude(module = "protobuf-java")
												exclude(module = "jakarta.activation-api")
								}
				}
				kapt {
								generateStubs = true
								correctErrorTypes = true
				}
}

dependencies {
				implementation(project(":shared-resources"))
				implementation(project(":features:loading"))
				implementation(project(":features:profile"))
				implementation(project(":features:job:job_ui"))
				implementation(project(":features:search"))
				implementation(project(":features:show_all:show_all_ui"))
				implementation(project(":features:onboarding:onboarding_ui"))
				implementation(project(":features:main"))
				implementation(project(":features:user:user_ui"))

				implementation(libs.hilt.android)
				kapt(libs.hilt.android.compiler)
}
