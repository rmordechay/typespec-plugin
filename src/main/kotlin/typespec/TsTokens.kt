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
    ALIAS,
    SUPPRESS
)

val NUMBER_SET = TokenSet.create(INT_LITERAL, FLOAT_LITERAL)

val BUILTIN_TYPE_SET = listOf(
    "numeric",
    "integer",
    "float",
    "int64",
    "int32",
    "int16",
    "int8",
    "safeint",
    "uint64",
    "uint32",
    "uint16",
    "uint8",
    "float32",
    "float64",
    "decimal",
    "decimal128",
    "plainDate",
    "plainTime",
    "utcDateTime",
    "offsetDateTime",
    "duration",
    "bytes",
    "string",
    "boolean",
    "null",
    "Array",
    "Record",
    "unknown",
    "void",
    "never",
    "url",
)

val STRING_LITERAL_SET = TokenSet.create(
    STRING_LITERAL1,
    STRING_LITERAL2,
    STRING_LITERAL3,
    STRING_LITERAL4,
    STRING_LITERAL5,
)
