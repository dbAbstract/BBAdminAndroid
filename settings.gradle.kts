pluginManagement {
    repositories {
        google()
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

rootProject.name = "BargainBuildAdmin"
include(":app")
include(":feature-home")
include(":core")
include(":wages")
include(":employees")
include(":work-hours")
include(":feature-work-status")
include(":feature-auth")
include(":user")
include(":feature-admin")
include(":app-admin")
