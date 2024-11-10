package typespec.highlighting

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement
import typespec.language.BUILTIN_TYPE_SET
import typespec.highlighting.TsTextAttributes.BUILTIN_TYPE_TEXT_ATTR
import typespec.highlighting.TsTextAttributes.DECORATOR_TEXT_ATTR
import typespec.psi.interfaces.TsDecorator
import typespec.psi.interfaces.TsIdentifierVariable

class TsHighlightAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        if (element !is TsIdentifierVariable) return
        if (element.parent is TsDecorator) {
            holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                .textAttributes(DECORATOR_TEXT_ATTR)
                .create()
        } else if (element.text in BUILTIN_TYPE_SET) {
            holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                .textAttributes(BUILTIN_TYPE_TEXT_ATTR)
                .create()
        }
    }
}