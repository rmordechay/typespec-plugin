package typespec.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.*
import com.intellij.psi.util.PsiTreeUtil

interface TsNamedElement : PsiNameIdentifierOwner

abstract class TsNamedElementImpl(node: ASTNode) : ASTWrapperPsiElement(node), TsNamedElement {
    override fun setName(name: String): PsiElement {
        TODO("Not yet implemented")
    }

    override fun getNameIdentifier(): PsiElement? {
        return PsiTreeUtil.getChildOfType(node.psi, TsVariable::class.java)
    }

    override fun getName(): String {
        return nameIdentifier?.text ?: return ""
    }

    override fun getTextOffset(): Int {
        return nameIdentifier?.textOffset ?: super.getTextOffset()
    }
}


abstract class TsVariable(node: ASTNode) : ASTWrapperPsiElement(node), ContributedReferenceHost {
    override fun getReferences(): Array<out PsiReference?> {
        return PsiReferenceService.getService().getContributedReferences(this)
    }
    override fun getReference(): TsReference? {
        return references.firstOrNull() as? TsReference
    }

    override fun getName(): String {
        return node.text.replace("IntellijIdeaRulezzz", "")
    }
}