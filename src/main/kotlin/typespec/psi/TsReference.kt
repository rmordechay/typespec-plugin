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

    override fun getRangeInElement(): TextRange {
        return super.getRangeInElement()
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