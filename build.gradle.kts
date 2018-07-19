import com.jfrog.bintray.gradle.BintrayExtension
import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    scala
    `maven-publish`
    id("com.jfrog.bintray") version "1.8.4"
    id("com.github.ben-manes.versions") version "0.20.0"
}

group = "io.github.alexbogovich"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    compile ("org.scala-lang:scala-library:2.11.12")
    testCompile("org.scalatest:scalatest_2.11:3.0.5")
    testCompile ("junit:junit:4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

publishing {
    (publications) {
        "mavenJava"(MavenPublication::class) {
            groupId = "${project.group}"
            artifactId = project.name
            version = "${project.version}"
            artifact(tasks["jar"])
            pom.withXml {
                asNode().appendNode("dependencies").let { depNode ->
                    configurations.compile.allDependencies.forEach {
                        depNode.appendNode("dependency").apply {
                            appendNode("groupId", it.group)
                            appendNode("artifactId", it.name)
                            appendNode("version", it.version)
                        }
                    }
                }
            }
        }
    }
}

bintray {
    user = findProperty("bintrayUser") as String?
    key = findProperty("bintrayApiKey") as String?
    publish = true
    setPublications("mavenJava")
    pkg(delegateClosureOf<BintrayExtension.PackageConfig> {
        repo = "repo"
        name = project.name
        userOrg = "alexbogovich"
        websiteUrl = "https://github.com/alexbogovich/$name"
        githubRepo = "alexbogovich/$name"
        vcsUrl = "https://github.com/alexbogovich/$name"
        description = "Utils for collections"
        setLabels("scala")
        setLicenses("MIT")
        desc = description
    })
}

tasks {
    withType<GenerateMavenPom> {
        destination = file("$buildDir/libs/${project.name}-$version.pom")
    }
    "dependencyUpdates"(DependencyUpdatesTask::class) {
        resolutionStrategy {
            componentSelection {
                all {
                    val rejected = listOf("alpha", "beta", "rc", "cr", "m", "snap")
                            .map { qualifier -> Regex("(?i).*[.-]$qualifier[.\\d-]*") }
                            .any { it.matches(candidate.version) }
                    if (rejected) {
                        reject("Release candidate")
                    }
                }
            }
        }
    }
}