package typespec

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors.*
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import typespec.TsTextAttributes.BAD_CHARACTER_TEXT_ATTR
import typespec.TsTextAttributes.BOOLEAN_TEXT_ATTR
import typespec.TsTextAttributes.KEYWORD_TEXT_ATTR
import typespec.TsTextAttributes.COMMENT_TEXT_ATTR
import typespec.TsTextAttributes.MULTILINE_COMMENT_TEXT_ATTR
import typespec.TsTextAttributes.NUMBERS_TEXT_ATTR
import typespec.TsTextAttributes.STRING_TEXT_ATTR
import typespec.TsTextAttributes.VARIABLE_TEXT_ATTR

class TsSyntaxHighlighterFactory : SyntaxHighlighterFactory() {
    override fun getSyntaxHighlighter(project: Project?, virtualFile: VirtualFile?): SyntaxHighlighter {
        return TsSyntaxHighlighter()
    }

}

class TsSyntaxHighlighter : SyntaxHighlighterBase() {
    override fun getHighlightingLexer(): Lexer {
        return TsLexer()
    }

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {
        return pack(mapTokenToTextAttr(tokenType))
    }

    /**
     *
     */
    private fun mapTokenToTextAttr(tokenType: IElementType): TextAttributesKey? {
        return when (tokenType) {
            TsTypes.COMMENT -> COMMENT_TEXT_ATTR
            TsTypes.MULTILINE_COMMENT -> MULTILINE_COMMENT_TEXT_ATTR
            TsTypes.BOOL_LITERAL -> BOOLEAN_TEXT_ATTR
            TsTypes.IDENTIFIER -> VARIABLE_TEXT_ATTR
            in STRING_LITERAL_SET -> STRING_TEXT_ATTR
            in NUMBER_SET -> NUMBERS_TEXT_ATTR
            in KEYWORDS_TOKENS -> KEYWORD_TEXT_ATTR
            TokenType.BAD_CHARACTER -> BAD_CHARACTER_TEXT_ATTR
            else -> null
        }
    }
}

/**
 *
 */
object TsTextAttributes {
    val VARIABLE_TEXT_ATTR = createTextAttributesKey("TS_VARIABLE", IDENTIFIER)
    val STRING_TEXT_ATTR = createTextAttributesKey("TS_STRING", STRING)
    val BOOLEAN_TEXT_ATTR = createTextAttributesKey("TS_BOOLEAN", KEYWORD)
    val NUMBERS_TEXT_ATTR = createTextAttributesKey("TS_NUMBER", NUMBER)
    val KEYWORD_TEXT_ATTR = createTextAttributesKey("TS_KEYWORD", KEYWORD)
    val DECORATOR_TEXT_ATTR = createTextAttributesKey("TS_DECORATOR", KEYWORD)
    val COMMENT_TEXT_ATTR = createTextAttributesKey("TS_COMMENT", LINE_COMMENT)
    val MULTILINE_COMMENT_TEXT_ATTR = createTextAttributesKey("TS_MULTILINE_COMMENT", LINE_COMMENT)
    val BAD_CHARACTER_TEXT_ATTR = createTextAttributesKey("TS_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER)
}