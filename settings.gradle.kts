pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Findet"

include(":app")

include(":core:mock")
include(":core:model")
include(":core:network")
include(":core:data")
include(":core:domain")
include(":core:presentation:model")
include(":core:presentation:ui")

include(":feature:expenses")
include(":feature:incomes")
include(":feature:account")
include(":feature:categories")
include(":feature:settings")
include(":feature:currencyselector")
include(":core:presentation:common")
