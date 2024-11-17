package typespec;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;import kotlinx.serialization.descriptors.PrimitiveKind;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static typespec.TsTypes.*;

%%

%{
  public _TsLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class _TsLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL=\R
WHITE_SPACE=\s+
COMMENT="//"[^\n]*
MULTILINE_COMMENT=\/\*\*([^*]|\*+[^*/])*\*+\/

PIPE="|"
QUESTION="?"
EQUALS="="
AMPERSAND="&"
COLON=":"
COMMA=","
SEMICOLON=";"
DOT="."
MINUS="-"
LT="<"
GT=">"
LPAREN="("
RPAREN=")"
LBRACE="{"
RBRACE="}"
LBRACK="["
RBRACK="]"
DOUBLE_AT='@@'
AT="@"
ELLIPSIS="..."
HASH="#"

IMPORT="import"
USING="using"
NAMESPACE="namespace"
SCALAR="scalar"
MODEL="model"
EXTENDS="extends"
IS="is"
INTERFACE="interface"
INIT="init"
OP="op"
ENUM="enum"
TYPEOF="typeof"
EXTERN="extern"
DEC="dec"
FN="fn"
ALIAS="alias"
UNION="union"
SUPPRESS="suppress"
DEPRECATED="deprecated"

IDENTIFIER=[a-zA-Z_$]+\w*
BOOL_LITERAL=false|true
STRING_LITERAL1=\".*\"
STRING_LITERAL2='.*'
STRING_LITERAL3=`.*`
STRING_LITERAL4=\"\"\"([^\"]|\"{0,2}[^\"])*\"\"\"
STRING_LITERAL5='''([^']|'{0,2}[^'])*'''
INT_LITERAL=\d+
FLOAT_LITERAL={INT_LITERAL}"."{INT_LITERAL}

%%
<YYINITIAL> {
  {WHITE_SPACE}          { return WHITE_SPACE; }
  {EOL}                  { return WHITE_SPACE; }
  {COMMENT}              { return COMMENT; }
  {MULTILINE_COMMENT}    { return MULTILINE_COMMENT; }

  {PIPE}                 { return PIPE;}
  {QUESTION}             { return QUESTION;}
  {EQUALS}               { return EQUALS;}
  {AMPERSAND}            { return AMPERSAND;}
  {COLON}                { return COLON;}
  {COMMA}                { return COMMA;}
  {SEMICOLON}            { return SEMICOLON;}
  {DOT}                  { return DOT;}
  {MINUS}                { return MINUS;}
  {LT}                   { return LT;}
  {GT}                   { return GT;}
  {LPAREN}               { return LPAREN;}
  {RPAREN}               { return RPAREN;}
  {LBRACE}               { return LBRACE;}
  {RBRACE}               { return RBRACE;}
  {LBRACK}               { return LBRACK;}
  {RBRACK}               { return RBRACK;}
  {AT}                   { return AT;}
  {DOUBLE_AT}            { return DOUBLE_AT;}
  {ELLIPSIS}             { return ELLIPSIS;}
  {HASH}                 { return HASH;}

  {IMPORT}               { return IMPORT; }
  {USING}                { return USING; }
  {NAMESPACE}            { return NAMESPACE; }
  {SCALAR}               { return SCALAR; }
  {MODEL}                { return MODEL; }
  {EXTENDS}              { return EXTENDS; }
  {IS}                   { return IS; }
  {INTERFACE}            { return INTERFACE; }
  {INIT}                 { return INIT; }
  {OP}                   { return OP; }
  {ENUM}                 { return ENUM; }
  {TYPEOF}               { return TYPEOF; }
  {EXTERN}               { return EXTERN; }
  {DEC}                  { return DEC; }
  {FN}                   { return FN; }
  {ALIAS}                { return ALIAS; }
  {UNION}                { return UNION; }
  {SUPPRESS}             { return SUPPRESS; }
  {DEPRECATED}           { return DEPRECATED; }

  {INT_LITERAL}          { return INT_LITERAL; }
  {FLOAT_LITERAL}        { return FLOAT_LITERAL; }
  {BOOL_LITERAL}         { return BOOL_LITERAL; }
  {STRING_LITERAL1}      { return STRING_LITERAL1; }
  {STRING_LITERAL2}      { return STRING_LITERAL2; }
  {STRING_LITERAL3}      { return STRING_LITERAL3; }
  {STRING_LITERAL4}      { return STRING_LITERAL4; }
  {STRING_LITERAL5}      { return STRING_LITERAL5; }
  {IDENTIFIER}           { return IDENTIFIER; }
}

[^] { return BAD_CHARACTER; }
