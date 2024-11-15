package typespec.editor

import com.intellij.codeInsight.editorActions.TypedHandlerDelegate
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.EditorModificationUtil
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile


class TsTypedHandler : TypedHandlerDelegate() {
    override fun charTyped(c: Char, project: Project, editor: Editor, file: PsiFile): Result {
        if (isQuoteChar(c)) {
            handleQuote(c, editor)
            return Result.STOP
        }
        return Result.CONTINUE
    }

    private fun handleQuote(quoteChar: Char, editor: Editor) {
        val offset = editor.caretModel.offset
        val text = editor.document.text
        if (offset > 0 && offset < text.length) {
            val prevChar = text[offset - 1]
            val nextChar = text[offset]
            if (prevChar == quoteChar && nextChar == quoteChar) {
                editor.caretModel.moveToOffset(offset + 1)
                return
            }
        }
        EditorModificationUtil.insertStringAtCaret(editor, quoteChar.toString())
        editor.caretModel.moveToOffset(offset)
    }

    private fun isQuoteChar(c: Char): Boolean {
        return c == '"' || c == '\'' || c == '`'
    }
}