package typespec

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement
import typespec.TsTextAttributes.BUILTIN_TYPE_TEXT_ATTR
import typespec.TsTextAttributes.DECORATOR_TEXT_ATTR
import typespec.psi.interfaces.TsDecorator
import typespec.psi.interfaces.TsIdentifierVariable

class TsHighlightAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        if (element.parent.parent is TsDecorator) {
            holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                .textAttributes(DECORATOR_TEXT_ATTR)
                .create()
        } else if (element is TsIdentifierVariable && element.text in BUILTIN_TYPE_SET) {
            holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                .textAttributes(BUILTIN_TYPE_TEXT_ATTR)
                .create()
        }
    }
}