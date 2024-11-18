import org.jetbrains.intellij.platform.gradle.TestFrameworkType

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.25"
    id("org.jetbrains.intellij.platform") version "2.1.0"
    id("org.jetbrains.grammarkit") version "2022.3.2.2"
    id("org.jetbrains.changelog") version "1.3.1"
}

group = "typespec"
version = "1.1.0"

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    intellijPlatform {
        intellijIdeaCommunity("2024.3")
        testFramework(TestFrameworkType.Platform)
        instrumentationTools()
    }
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.opentest4j:opentest4j:1.3.0")
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
        untilBuild.set("243.*")
        changeNotes = changelog.get(project.version.toString()).toHTML()
        pluginDescription = file("plugin-description.html").readText()
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
        dependsOn("generateParser")
        sourceFile = File("src/main/kotlin/typespec/language/Lexer.flex")
        targetOutputDir = File("src/main/gen/typespec")
    }
}

sourceSets["main"].java.srcDirs("src/main/gen")

tasks.register("generateGrammarClean") {
    val dirToDelete = file("src/main/gen")
    doFirst {
        println("Deleting directory: ${dirToDelete.path}")
        dirToDelete.deleteRecursively()
    }
    finalizedBy("generateLexer")
}

tasks.register("generateAndRun") {
    tasks.findByName("runIde")?.dependsOn("generateGrammarClean")
    tasks.findByName("compileKotlin")?.mustRunAfter("generateParser")
    tasks.findByName("compileKotlin")?.mustRunAfter("generateLexer")
    dependsOn("generateGrammarClean", "runIde")
}