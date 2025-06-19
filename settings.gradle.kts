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
include(":core:presentation:model")
include(":core:presentation:ui")
include(":core:mock")
include(":core:network")
include(":demo")
include(":core:model")
include(":core:data")
include(":app")
include(":feature:expenses")
include(":feature:incomes")
include(":feature:account")
include(":feature:categories")
include(":feature:settings")
include(":core:domain")
include(":core:datastore")
include(":feature:expenses_history")
include(":feature:incomes_history")
