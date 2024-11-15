[![Downloads](https://img.shields.io/jetbrains/plugin/d/25798-typespec)](https://plugins.jetbrains.com/plugin/25798-typespec/reviews)
[![Rating](https://img.shields.io/jetbrains/plugin/r/stars/25798-typespec)](https://plugins.jetbrains.com/plugin/25798-typespec/reviews)

# IDEA Plugin Support for TypeSpec 

[Plugin Page](https://plugins.jetbrains.com/plugin/25798-typespec)

## Build & Run
``` shell
git clone https://github.com/walt-grace/glsl-plugin-idea.git
```
Assuming you're developing with Intellij (and you want to develop with Intellij):
1. **Generate grammar**. Execute the `generateGrammarClean` task from _gradle.build_ file or under _Tasks/other_ if you use the Gradle tab.
2. **Run**. Execute the `runIde` task (Intellij will build the project and then run the instance).

\* If you're only interested in building the project without running it you can use task `buildPlugin` after step 1.

## Test
Just execute the `test` task from the Gradle tab or run specific classes or tests from within the ide.