package typespec

import com.intellij.lang.Language
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.IconLoader.getIcon
import javax.swing.Icon


class TsLanguage : Language("TypeSpec") {

    companion object {
        val INSTANCE = TsLanguage()
    }
}

object TsIcon {
    val FILE: Icon = getIcon("icons/pluginIcon.svg", TsLanguage::class.java)
}

class TsFileType : LanguageFileType(TsLanguage.INSTANCE) {

    override fun getName(): String {
        return "TypeSpec"
    }

    override fun getDescription(): String {
        return "TypeSpec file"
    }

    override fun getDefaultExtension(): String {
        return "tsp"
    }

    override fun getIcon(): Icon {
        return TsIcon.FILE
    }

    companion object {
        val INSTANCE = TsFileType()
    }
}
