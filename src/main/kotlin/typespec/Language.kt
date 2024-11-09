package typespec

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.lang.ASTNode
import com.intellij.lang.Language
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.FlexAdapter
import com.intellij.lexer.Lexer
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.IconLoader.getIcon
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import javax.swing.Icon


class TsLanguage : Language("TypeSpec") {

    companion object {
        val INSTANCE = TsLanguage()
    }
}

object TsIcon {
    val FILE: Icon = getIcon("icons/pluginIcon.svg", TsLanguage::class.java)
}

class TsFileType : LanguageFileType(TsLanguage.INSTANCE) {

    override fun getName(): String {
        return "TypeSpec"
    }

    override fun getDescription(): String {
        return "TypeSpec file"
    }

    override fun getDefaultExtension(): String {
        return "tsp"
    }

    override fun getIcon(): Icon {
        return TsIcon.FILE
    }

    companion object {
        val INSTANCE = TsFileType()
    }
}

class TsTokenType(debugName: String): IElementType(debugName, TsLanguage.INSTANCE)

class TsElementType(debugName: String) : IElementType(debugName, TsLanguage.INSTANCE)

class TsLexer : FlexAdapter(_TsLexer())

class TsFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, TsLanguage.INSTANCE) {
    override fun getFileType(): FileType {
        return TsFileType.INSTANCE
    }
}

class TsParser : ParserDefinition {
    override fun createLexer(project: Project?): Lexer {
        return TsLexer()
    }

    override fun createParser(project: Project?): PsiParser {
        return _TsParser()
    }

    override fun getFileNodeType(): IFileElementType {
        return FILE
    }

    override fun getCommentTokens(): TokenSet {
        return TokenSet.create()
    }

    override fun getStringLiteralElements(): TokenSet {
        return TokenSet.create(TsTypes.STRING_LITERAL1, TsTypes.STRING_LITERAL2)
    }

    override fun createElement(node: ASTNode?): PsiElement {
        return TsTypes.Factory.createElement(node);
    }

    override fun createFile(viewProvider: FileViewProvider): PsiFile {
        return TsFile(viewProvider)
    }

    companion object {
        val FILE = IFileElementType(TsLanguage.INSTANCE)
    }

}