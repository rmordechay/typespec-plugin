package typespec.editor

import com.intellij.ide.actions.CreateFileFromTemplateAction
import com.intellij.ide.actions.CreateFileFromTemplateDialog
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory
import typespec.language.TsIcon

private const val TYPE_SPEC_FILE = "TypeSpec File"

class TsNewFile : CreateFileFromTemplateAction("TypeSpec File", "", TsIcon.FILE) {
    override fun buildDialog(project: Project, directory: PsiDirectory, builder: CreateFileFromTemplateDialog.Builder) {
        builder.setTitle(TYPE_SPEC_FILE)
        builder.addKind("TypeSpec", TsIcon.FILE, "ts-template")
    }

    override fun getActionName(directory: PsiDirectory?, newName: String, templateName: String?): String {
        return TYPE_SPEC_FILE
    }
}