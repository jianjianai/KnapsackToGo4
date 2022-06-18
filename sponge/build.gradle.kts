import org.spongepowered.gradle.plugin.config.PluginLoaders
import org.spongepowered.plugin.metadata.model.PluginDependency
import java.text.SimpleDateFormat
import java.util.Date



plugins {
    `java-library`
    id("org.spongepowered.gradle.plugin") version "2.0.2"
}

group = "cn.jja8"
version = "1.0-SNAPSHOT"


repositories {
    mavenCentral()
}

sponge {
    //获取当前时间作为版本号
    val formatter = SimpleDateFormat("yyyy.MM.dd.HHmm")
    val version = formatter.format(Date())+"-new";//"0.03"//

    apiVersion("9.0.0")
    license("All Rights Reserved")
    loader {
        name(PluginLoaders.JAVA_PLAIN)
        version(version)
    }
    plugin("knapsacktogo4") {
        displayName("KnapsackToGo4")
        entrypoint("cn.jja8.knapsackToGo4.sponge.KnapsackToGo4")
                description("My plugin description")
                links {
                        // homepage("https://spongepowered.org")
                        // source("https://spongepowered.org/source")
            // issues("https://spongepowered.org/issues")
        }
                dependency("spongeapi") {
            loadOrder(PluginDependency.LoadOrder.AFTER)
            optional(false)
        }
            }
}

val javaTarget = 17 // Sponge targets a minimum of Java 17
java {
    sourceCompatibility = JavaVersion.toVersion(javaTarget)
    targetCompatibility = JavaVersion.toVersion(javaTarget)
    if (JavaVersion.current() < JavaVersion.toVersion(javaTarget)) {
        toolchain.languageVersion.set(JavaLanguageVersion.of(javaTarget))
    }
}

tasks.withType(JavaCompile::class).configureEach {
    options.apply {
        encoding = "utf-8" // Consistent source file encoding
        if (JavaVersion.current().isJava10Compatible) {
            release.set(javaTarget)
        }
    }
}

// Make sure all tasks which produce archives (jar, sources jar, javadoc jar, etc) produce more consistent output
tasks.withType(AbstractArchiveTask::class).configureEach {
    isReproducibleFileOrder = true
    isPreserveFileTimestamps = false
}


dependencies {
    compileOnly(project(":all"))
}
