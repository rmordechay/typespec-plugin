package typespec.completion

import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.util.ProcessingContext
import typespec.TsTypes.COLON
import typespec.language.BUILTIN_TYPE_SET


class TsCompletionContributor : CompletionContributor() {
    init {
        val psiElement = psiElement().afterLeaf(psiElement(COLON))
        extend(CompletionType.BASIC, psiElement, TsCompletionProvider())
    }
}

class TsCompletionProvider : CompletionProvider<CompletionParameters?>() {
    override fun addCompletions(
        parameters: CompletionParameters, context: ProcessingContext, result: CompletionResultSet
    ) {
        val lookupElements = BUILTIN_TYPE_SET.map { LookupElementBuilder.create(it) }
        result.addAllElements(lookupElements)
    }
}