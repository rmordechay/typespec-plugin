package typespec.psi

import com.intellij.lang.cacheBuilder.DefaultWordsScanner
import com.intellij.lang.cacheBuilder.WordsScanner
import com.intellij.lang.findUsages.FindUsagesProvider
import com.intellij.openapi.util.TextRange
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.*
import com.intellij.psi.tree.TokenSet
import com.intellij.psi.util.PsiTreeUtil.*
import com.intellij.util.ProcessingContext
import typespec.TsTypes
import typespec._TsLexer
import typespec.language.COMMENTS_SET
import typespec.language.STRING_LITERAL_SET
import typespec.language.TsLexer
import typespec.psi.interfaces.TsIdentifierVariable
import typespec.psi.interfaces.TsStatement

class TsReference(element: TsVariable, textRange: TextRange) : PsiReferenceBase<TsVariable>(element, textRange) {

    override fun resolve(): PsiElement? {
        val baseStmt = getParentOfType(element, TsStatement::class.java)
        var prevStmt = getPrevSiblingOfType(baseStmt, TsStatement::class.java)
        var nextStmt = getNextSiblingOfType(baseStmt, TsStatement::class.java)
        while (true) {
            var childStmt = prevStmt?.firstChild
            if (childStmt is TsNamedElement && childStmt.name == element.name) {
                return childStmt
            }
            childStmt = nextStmt?.firstChild
            if (childStmt is TsNamedElement && childStmt.name == element.name) {
                return childStmt
            }
            prevStmt = getPrevSiblingOfType(prevStmt, TsStatement::class.java)
            nextStmt = getNextSiblingOfType(nextStmt, TsStatement::class.java)
            if (nextStmt == null && prevStmt == null) break
        }
        return null
    }
}

class TsReferenceContributor : PsiReferenceContributor() {
    override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
        val psiElement = PlatformPatterns.psiElement(TsIdentifierVariable::class.java)
        registrar.registerReferenceProvider(psiElement, TsReferenceProvider())
    }

    inner class TsReferenceProvider : PsiReferenceProvider() {
        override fun getReferencesByElement(element: PsiElement, context: ProcessingContext): Array<PsiReference> {
            if (element !is TsVariable) return emptyArray()
            return arrayOf(TsReference(element, element.textRangeInParent))
        }
    }
}

class TsFindUsage: FindUsagesProvider {
    override fun getWordsScanner(): WordsScanner {
        return DefaultWordsScanner(TsLexer(), TokenSet.create(TsTypes.IDENTIFIER_VARIABLE), COMMENTS_SET, STRING_LITERAL_SET)
    }

    override fun canFindUsagesFor(psiElement: PsiElement): Boolean {
        return psiElement is TsNamedElement
    }

    override fun getHelpId(psiElement: PsiElement): String? {
        return null
    }

    override fun getType(element: PsiElement): String {
        return ""
    }

    override fun getDescriptiveName(element: PsiElement): String {
        if (element !is TsNamedElement) return ""
        return element.text
    }

    override fun getNodeText(element: PsiElement, useFullName: Boolean): String {
        if (element !is TsNamedElement) return ""
        return element.text
    }

}