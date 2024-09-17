buildscript {
				repositories {
								google()
								mavenCentral()
				}

    dependencies {
        classpath(libs.gradle)
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.androidx.navigation.safe.args.gradle.plugin)
    }
}
//
//if (hasProperty("buildScan")) {
//				buildScan {
//								termsOfServiceUrl = "https://gradle.com/terms-of-service"
//								termsOfServiceAgree = "yes"
//				}
//}

plugins {
				alias(libs.plugins.com.android.application) apply false
				alias(libs.plugins.org.jetbrains.kotlin.android) apply false
				alias(libs.plugins.com.google.dagger.hilt.android) apply false
				alias(libs.plugins.android.library) apply false
				alias(libs.plugins.com.google.gms.google.services) apply false
}
