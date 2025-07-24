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
include(":core:data")
include(":core:model")
include(":core:network")
include(":core:database")
include(":core:presentation:model")
include(":core:presentation:designsystem")
include(":core:presentation:common")
include(":core:mock")
include(":feature:account")
include(":feature:income")
include(":feature:expense")
include(":feature:category")
include(":feature:bottomsheet")
include(":feature:setting")
include(":core:sync")
include(":chart")
include(":core:splash")
include(":core:datastore")
