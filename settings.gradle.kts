pluginManagement {
				repositories {
								google()
								mavenCentral()
								gradlePluginPortal()
								maven(url = "https://jitpack.io")
				}
}

dependencyResolutionManagement {
				repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
				repositories {
								google()
								mavenCentral()
								maven(url = "https://jitpack.io")
				}
}

rootProject.name = "JobsFinderApp"
include(
				listOf(
								":app",
								":core:data",
								":core:domain",
								":core:ui",
								":core-test",
								":shared-resources",
								":features:user:ui",
								":features:user:domain",
								":features:user:data",
								":features:job",
								":features:job:data",
								":features:job:domain",
								":features:job:ui",
								":features:favorite:data",
								":features:favorite:ui",
								":features:onboarding:ui",
								":features:main:ui",
								":features:auth:domain",
								":features:auth:data",
				)
)
