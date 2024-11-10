plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.25"
    id("org.jetbrains.intellij") version "1.17.4"
    id("org.jetbrains.grammarkit") version "2022.3.2.2"
    id("org.jetbrains.changelog") version "1.3.1"
}

group = "typespec"
version = "1.0.0"

repositories {
    mavenCentral()
}

intellij {
    version.set("2023.2.6")
    type.set("IC")
}

tasks {
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }


    patchPluginXml {
        sinceBuild.set("222")
        untilBuild.set("242.*")
        changeNotes = changelog.get(project.version.toString()).toHTML()
        pluginDescription = file("plugin-description.html").readText()
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }

    generateParser {
        pathToParser = "src/main/kotlin/typespec/language/Grammar.bnf"
        sourceFile = File("src/main/kotlin/typespec/language/Grammar.bnf")
        pathToPsiRoot = "src/main/gen"
        targetRootOutputDir = File("src/main/gen")
    }

    generateLexer {
        sourceFile = File("src/main/kotlin/typespec/language/Lexer.flex")
        targetOutputDir = File("src/main/gen/typespec")
        dependsOn("generateParser")
    }
}

sourceSets["main"].java.srcDirs("src/main/gen")

tasks.register("generateGrammarClean") {
    val dirToDelete = file("src/main/gen")
    doFirst {
        println("Deleting directory: ${dirToDelete.path}")
        dirToDelete.deleteRecursively()
    }
    finalizedBy("generateParser", "generateLexer")
}