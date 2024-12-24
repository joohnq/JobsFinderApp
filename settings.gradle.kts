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
								":shared-resources",
								":features",
								":features:user:ui",
								":features:user:domain",
								":features:user:data",
								":features:job",
								":features:job:data",
								":features:job:domain",
								":features:job:ui",
								":features:favorite",
								":features:favorite:data",
								":features:favorite:ui",
								":features:onboarding:ui",
								":core:data",
								":core:domain",
								":core:ui"
				)
)
include(":core-test")
include(":features:main")
include(":features:auth")
include(":features:auth:domain")
include(":features:auth:data")
include(":core:data")
include(":core:domain")
include(":core:ui")
include(":features:onboarding:ui")
include(":features:onboarding:ui")
include(":features:main:ui")
