package typespec.psi

import com.intellij.lang.cacheBuilder.DefaultWordsScanner
import com.intellij.lang.cacheBuilder.WordsScanner
import com.intellij.lang.findUsages.FindUsagesProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.TextRange
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.*
import com.intellij.psi.tree.TokenSet
import com.intellij.psi.util.PsiTreeUtil.*
import com.intellij.psi.util.childrenOfType
import com.intellij.testFramework.utils.vfs.getPsiFile
import com.intellij.util.ProcessingContext
import typespec.TsTypes
import typespec.language.COMMENTS_SET
import typespec.language.STRING_LITERAL_SET
import typespec.language.TsLexer
import typespec.psi.interfaces.TsIdentifierVariable
import typespec.psi.interfaces.TsStringLiteral
import typespec.psi.interfaces.TsTypeSpecScriptItem

class TsReference(element: TsVariable, textRange: TextRange) : PsiReferenceBase<TsVariable>(element, textRange) {
    override fun resolve(): PsiElement? {
        val baseItem = getParentOfType(element, TsTypeSpecScriptItem::class.java)
        var prevItem = getPrevSiblingOfType(baseItem, TsTypeSpecScriptItem::class.java)
        var nextItem = getNextSiblingOfType(baseItem, TsTypeSpecScriptItem::class.java)
        while (true) {
            var lookupInTsItem = getReferenceFromItem(prevItem)
            if (lookupInTsItem != null) {
                return lookupInTsItem
            }
            lookupInTsItem = getReferenceFromItem(nextItem)
            if (lookupInTsItem != null) {
                return lookupInTsItem
            }
            prevItem = getPrevSiblingOfType(prevItem, TsTypeSpecScriptItem::class.java)
            nextItem = getNextSiblingOfType(nextItem, TsTypeSpecScriptItem::class.java)
            if (nextItem == null && prevItem == null) break
        }
        return null
    }

    /**
     *
     */
    private fun getReferenceFromItem(prevItem: TsTypeSpecScriptItem?): PsiElement? {
        if (prevItem?.statement != null) {
            val firstStmtChild = prevItem.statement?.firstChild
            if (firstStmtChild is TsNamedElement && firstStmtChild.name == element.name) {
                return firstStmtChild
            }
        } else if (prevItem?.importStatement != null) {
            val importStmt = prevItem.importStatement!!
            val stringLiteral = getStringLiteral(importStmt.stringLiteral)
            val importFile = getImportFile(stringLiteral, importStmt.containingFile.virtualFile, importStmt.project)
            val importedElement = walkFile(importFile)
            if (importedElement != null) {
                return importedElement
            }
        }
        return null
    }

    /**
     *
     */
    private fun walkFile(psiFile: PsiFile?): PsiElement? {
        val items = psiFile?.childrenOfType<TsTypeSpecScriptItem>() ?: return null
        for (item in items) {
            val firstStmtChild = item.statement?.firstChild ?: continue
            if (firstStmtChild is TsNamedElement && firstStmtChild.name == element.name) {
                return firstStmtChild
            }
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

/**
 *
 */
fun getStringLiteral(stringLiteral: TsStringLiteral): String? {
    return when {
        stringLiteral.stringLiteral1 != null -> stringLiteral.stringLiteral1?.text?.removeSurrounding("\"")
        stringLiteral.stringLiteral2 != null -> stringLiteral.stringLiteral2?.text?.removeSurrounding("'")
        stringLiteral.stringLiteral4 != null -> stringLiteral.stringLiteral3?.text?.removeSurrounding("`")
        stringLiteral.stringLiteral3 != null -> stringLiteral.stringLiteral4?.text?.removeSurrounding("\"\"\"")
        stringLiteral.stringLiteral4 != null -> stringLiteral.stringLiteral5?.text?.removeSurrounding("'''")
        else -> null
    }
}

/**
 *
 */
fun getImportFile(targetPathString: String?, baseFile: VirtualFile?, project: Project?): PsiFile? {
    if (project == null || baseFile == null || targetPathString == null) return null
    return baseFile.parent?.findFileByRelativePath(targetPathString)?.getPsiFile(project)
}
