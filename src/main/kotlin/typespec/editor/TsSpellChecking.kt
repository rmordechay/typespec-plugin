package typespec.editor

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.spellchecker.inspections.TextSplitter
import com.intellij.spellchecker.tokenizer.SpellcheckingStrategy
import com.intellij.spellchecker.tokenizer.TokenConsumer
import com.intellij.spellchecker.tokenizer.Tokenizer
import typespec.psi.interfaces.TsStringLiteral

class TsSpellChecking : SpellcheckingStrategy() {
    override fun getTokenizer(element: PsiElement?): Tokenizer<*> {
        if (element !is TsStringLiteral) return EMPTY_TOKENIZER
        return TsTokenizer()
    }
}

class TsTokenizer : Tokenizer<TsStringLiteral>() {
    override fun tokenize(element: TsStringLiteral, consumer: TokenConsumer) {
        val offset = when {
            element.stringLiteral1 != null -> 1
            element.stringLiteral2 != null -> 1
            element.stringLiteral3 != null -> 1
            element.stringLiteral4 != null -> 3
            element.stringLiteral5 != null -> 3
            else -> 0
        }

        val textRange = TextRange.create(offset, element.textLength)
        consumer.consumeToken(element, element.text, false, 0, textRange, TextSplitter.getInstance())
    }
}