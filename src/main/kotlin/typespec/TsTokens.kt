package typespec

import com.intellij.psi.tree.TokenSet
import typespec.TsTypes.*

val KEYWORDS_TOKENS = TokenSet.create(
    IMPORT,
    USING,
    NAMESPACE,
    SCALAR,
    MODEL,
    EXTENDS,
    IS,
    INTERFACE,
    INIT,
    OP,
    ENUM,
    TYPEOF,
    EXTERN,
    DEC,
    FN,
    ALIAS
)

val NUMBER_SET = TokenSet.create()

val STRING_LITERAL_SET = TokenSet.create(
    STRING_LITERAL1,
    STRING_LITERAL2,
    STRING_LITERAL3,
    STRING_LITERAL4,
    STRING_LITERAL5,
)
