package typespec;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

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

IDENTIFIER=[a-zA-Z_$]+\w*
BOOL_LITERAL=false|true
STRING_LITERAL1='.*'
STRING_LITERAL2=`.*`
STRING_LITERAL3=\".*\"
STRING_LITERAL4='''.*'''
STRING_LITERAL5=\"\"\".*\"\"\"
INT_LITERAL=\d+
FLOAT_LITERAL={INT_LITERAL}"."{INT_LITERAL}

PIPE="|"
QUESTION="?"
EQUALS="="
AMPERSAND="&"
COLON=":"
COMMA=","
SEMICOLON=";"
DOT="."
LT="<"
GT=">"
LPAREN="("
RPAREN=")"
LBRACE="{"
RBRACE="}"
LBRACK="["
RBRACK="]"
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

%%
<YYINITIAL> {
  {WHITE_SPACE}          { return WHITE_SPACE; }
  {EOL}                  { return WHITE_SPACE; }

  {PIPE}                 { return PIPE;}
  {QUESTION}             { return QUESTION;}
  {EQUALS}               { return EQUALS;}
  {AMPERSAND}            { return AMPERSAND;}
  {COLON}                { return COLON;}
  {COMMA}                { return COMMA;}
  {SEMICOLON}            { return SEMICOLON;}
  {DOT}                  { return DOT;}
  {LT}                   { return LT;}
  {GT}                   { return GT;}
  {LPAREN}               { return LPAREN;}
  {RPAREN}               { return RPAREN;}
  {LBRACE}               { return LBRACE;}
  {RBRACE}               { return RBRACE;}
  {LBRACK}               { return LBRACK;}
  {RBRACK}               { return RBRACK;}
  {AT}                   { return AT;}
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
