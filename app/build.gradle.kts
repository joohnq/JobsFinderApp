plugins {
				alias(libs.plugins.com.android.application)
				alias(libs.plugins.org.jetbrains.kotlin.android)
				alias(libs.plugins.com.google.dagger.hilt.android)
				id("kotlin-kapt")
}

android {
				namespace = "com.joohnq.jobsfinderapp"
				compileSdk = libs.versions.compileSdk.get().toInt()

				defaultConfig {
								applicationId = "com.joohnq.jobsfinderapp"
								minSdk = libs.versions.minSdk.get().toInt()
								targetSdk = libs.versions.targetSdk.get().toInt()
								versionCode = libs.versions.versionCode.get().toInt()
								versionName = libs.versions.versionName.get()

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
//												implementation {
//																exclude(module = "proto-google-common-protos")
//																exclude(module = "protolite-well-known-types")
//												}
												exclude(module = "protobuf-javalite")
												exclude(module = "protolite-well-known-types")
												exclude(module = "proto-google-common-protos")
												exclude(module = "kotlin-gradle-plugin")
												exclude(module = "kxml2")
												exclude(module = "xmlpull")
												exclude(module = "xml-apis")
												exclude(module = "kotlin-gradle-plugin-api")
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

				implementation(libs.androidx.navigation.fragment.ktx)
				implementation(libs.androidx.navigation.ui.ktx)

				implementation(libs.hilt.android)
				kapt(libs.hilt.android.compiler)
}
