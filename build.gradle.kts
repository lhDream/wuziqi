import io.github.fvarrui.javapackager.gradle.PackageTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.21"
    application
    id("org.openjfx.javafxplugin") version "0.0.13"
}

group = "io.github.lhDream"
version = "1.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("no.tornado:tornadofx:1.7.20")
    implementation("com.alibaba.fastjson2:fastjson2:2.0.42")
}

buildscript{
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies{
        classpath("io.github.fvarrui:javapackager:1.7.5")
    }
}

apply(plugin = "io.github.fvarrui.javapackager.plugin")

tasks.register("wuziqi",PackageTask::class.java){
    mainClass = "io.github.lhDream.MainAppKt"
    displayName = "本地五子棋"
    appName = "io.github.lhdream.wuziqi"
    isBundleJre = true
    isCustomizedJre = false
    isCopyDependencies = true
    isGenerateInstaller = true
    assetsDir = File("${project.rootDir}/assets")
    platform = io.github.fvarrui.javapackager.model.Platform.auto
    vmArgs = listOf("-Xms256M","-Xmx512M")
    linuxConfig(null).apply {
        installationPath = File("/opt/apps")
        isGenerateInstaller = true
        isGenerateDeb = true
        isGenerateAppImage = false
        isGenerateRpm = false
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

javafx{
    version = "17.0.2"
    modules("javafx.controls")
}