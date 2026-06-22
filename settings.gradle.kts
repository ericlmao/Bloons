pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven("https://oss.sonatype.org/content/groups/public/") {
            name = "sonatype"
        }
        maven("https://mvn.lumine.io/repository/maven-public/") {
            name = "lumine"
        }
        maven("https://repo.papermc.io/repository/maven-public/") {
            name = "papermc"
        }
        maven("https://repo.moonrise.gg/repository/maven-releases") {
            name = "moonriseReleases"
        }
        maven("https://repo.moonrise.gg/repository/maven-snapshots") {
            name = "moonriseSnapshots"
        }
    }
}

rootProject.name = "bloons"
