pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS) // Prefer settings-level repositories
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "My Application"
include(":app")
