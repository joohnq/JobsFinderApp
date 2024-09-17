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
								":features:user:user_ui",
								":features:user:user_domain",
								":features:user:user_data",
								":features:job",
								":features:job:job_data",
								":features:job:job_domain",
								":features:job:job_ui",
								":features:favorite",
								":features:favorite:favorite_data",
								":features:favorite:favorite_ui",
								":features:onboarding",
								":features:onboarding:onboarding_data",
								":features:onboarding:onboarding_domain",
								":features:onboarding:onboarding_ui",
								":features:profile",
								":features:home",
								":features:show_all",
								":features:show_all:show_all_domain",
								":features:show_all:show_all_ui",
								":features:search",
								":core"
				)
)
include(":core_test")
include(":features:main")
include(":features:loading")
