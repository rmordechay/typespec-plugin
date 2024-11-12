package typespec.editor

import com.intellij.lang.BracePair
import com.intellij.lang.PairedBraceMatcher
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType
import typespec.TsTypes

class TsBraceMatcher : PairedBraceMatcher {
    override fun getPairs(): Array<BracePair> {
        return arrayOf(
            BracePair(TsTypes.LBRACE, TsTypes.RBRACE, true),
            BracePair(TsTypes.LPAREN, TsTypes.RPAREN, false),
            BracePair(TsTypes.LBRACK, TsTypes.RBRACK, false),
            BracePair(TsTypes.LBRACK, TsTypes.RBRACK, false),
        )
    }

    override fun isPairedBracesAllowedBeforeType(lbraceType: IElementType, contextType: IElementType?): Boolean {
        return true
    }

    override fun getCodeConstructStart(file: PsiFile?, openingBraceOffset: Int): Int {
        return openingBraceOffset
    }
}
