plugins {
    kotlin("jvm") version "1.6.10"
}

group = "org.example"
version = "1.0-SNAPSHOT"

// Copy compiled Kotlin classes into the destination directory for Java classes to make the Java compiler aware of them
// when parsing the "module-info.java" file (which must reside in the "java" source folder to be recognized)
val compileKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
val compileJava: JavaCompile by tasks
compileKotlin.destinationDirectory.set(compileJava.destinationDirectory)

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    // Uncomment the lines to exclude the transitive dependency "org.eclipse.xtext.util". This will fix the build error
    // "the unnamed module reads package org.eclipse.xtext.util from both org.eclipse.xtext and org.eclipse.xtext.util"
    // (resulting from the fact that both org.eclipse.xtext and its transitive dependency "org.eclipse.xtext.util"
    // comprise a package called org.eclipse.xtext.util which JPMS recognizes as a forbidden "split package" case).
    // However, excluding the transitive dependency will make Xtext stop working so we need to wait until Xtext is fixed
    // upstream to provide full JPMS support.
    implementation("org.eclipse.xtext:org.eclipse.xtext:2.26.0") //{
        //exclude("org.eclipse.xtext", "org.eclipse.xtext.util")
    //}
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "11"
    }
}

// Configure Gradle to recognize this project as being JPMS-enabled
tasks.withType<JavaCompile> {
    modularity.inferModulePath.set(true)
}