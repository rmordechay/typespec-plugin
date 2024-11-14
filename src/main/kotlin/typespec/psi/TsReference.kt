package typespec.psi

import com.intellij.openapi.util.TextRange
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.*
import com.intellij.psi.util.PsiTreeUtil.*
import com.intellij.util.ProcessingContext
import typespec.psi.interfaces.TsIdentifierVariable
import typespec.psi.interfaces.TsStatement

class TsReference(element: TsVariable, textRange: TextRange) : PsiReferenceBase<TsVariable>(element, textRange) {

    override fun resolve(): PsiElement? {
        val baseStmt = getParentOfType(element, TsStatement::class.java)
        var prevStmt : TsStatement?
        var nextStmt : TsStatement?
        while (true) {
            nextStmt = getNextSiblingOfType(baseStmt, TsStatement::class.java)
            var childStmt = nextStmt?.firstChild
            if (childStmt is TsNamedElement && childStmt.name == element.name) {
                return childStmt
            }
            prevStmt = getPrevSiblingOfType(baseStmt, TsStatement::class.java)
            childStmt = prevStmt?.firstChild
            if (childStmt is TsNamedElement && childStmt.name == element.name) {
                return childStmt
            }
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