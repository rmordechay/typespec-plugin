package typespec

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement
import typespec.TsTextAttributes.DECORATOR_TEXT_ATTR
import typespec.psi.interfaces.TsDecorator

class TsHighlightAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        if (element.parent.parent !is TsDecorator) return
        holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
            .textAttributes(DECORATOR_TEXT_ATTR)
            .create()
    }
}