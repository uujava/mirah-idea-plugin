package org.mirah.idea.plugin.psi;

import com.intellij.lang.Language;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.sun.javafx.fxml.expression.Expression;
import mirah.impl.Tokens;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import org.jetbrains.annotations.Nullable;
import org.mirah.idea.plugin.MirahLanguage;
import org.mirah.mmeta.BaseParser;

import java.util.EnumSet;

/**
 * Created by user on 5/20/2015.
 */
public class MirahTokenType extends IElementType {

    public static final EnumSet<Tokens> KEYWORDS;
    public static final EnumSet<Tokens> LITERALS;

    public static final MirahTokenType KEYWORD = new MirahTokenType("keyword");
    public static final MirahTokenType IDENTIFIER = new MirahTokenType("identifier");
    public static final MirahTokenType LITERAL = new MirahTokenType("literal");
    public static final MirahTokenType CHARACTER = new MirahTokenType("character");
    public static final MirahTokenType INSTANCE_VAR = new MirahTokenType("instance_var");
    public static final MirahTokenType CLASS_VAR = new MirahTokenType("class_var");
    public static final MirahTokenType STRING = new MirahTokenType("string");
    public static final MirahTokenType COMMENT = new MirahTokenType("comment");
    public static final MirahTokenType JAVA_COMMENT = new MirahTokenType("java_comment");
    public static final MirahTokenType UNKNOWN = new MirahTokenType("unknown");
    public static final MirahTokenType WHITESPACE = new MirahTokenType("whitespace");
    public static final MirahTokenType CONSTANT = new MirahTokenType("constant");

    // TODO implement in lexer
    public static final MirahTokenType METHOD_DECLARATION = new MirahTokenType("method_declaration");
    public static final MirahTokenType CLASS_DECLARATION = new MirahTokenType("class_declaration");
    public static final MirahTokenType TYPE_HINT = new MirahTokenType("type_hint");

    public MirahTokenType(@NotNull @NonNls String debugName) {
        super(debugName, MirahLanguage.INSTANCE);
    }

    public String toString(){
        return "mirah.token.type."+super.toString();
    }

    public static IElementType mapFromMirah(Enum<Tokens> type) {
//        if ( CLASS_DECLARATION == ty.ordinal()){
//            return CLASS_DECLARATION;
//        } else if ( METHOD_DECLARATION == t.ordinal()){
//            return METHOD_DECLARATION;
//        } else if ( TYPE_HINT == t.ordinal()){
//            return TYPE_HINT;
//        }
        if ( Tokens.tIDENTIFIER == type) {
            return IDENTIFIER;
        } else if (Tokens.tNL == type){
            return TokenType.NEW_LINE_INDENT;
        } else if (Tokens.tWhitespace == type){
            return TokenType.WHITE_SPACE;
        } else if (Tokens.tCONSTANT == type){
            return CONSTANT;
        } else if ( Tokens.tCharacter == type || Tokens.tSQuote == type) {
            return CHARACTER;
        } else if ( Tokens.tInstVar == type){
            return INSTANCE_VAR;
        } else if ( Tokens.tClassVar == type){
            return CLASS_VAR;
        } else if ( Tokens.tStringContent == type || Tokens.tDQuote==type){
            return STRING;
        } else if ( Tokens.tComment == type) {
            return COMMENT;
        }else if ( Tokens.tJavaDoc == type) {
            return JAVA_COMMENT;
        } else if ( LITERALS.contains(type)){
            return LITERAL;
        } else if ( Tokens.tWhitespace == type || Tokens.tNL == type){
            return WHITESPACE;
        } else if ( KEYWORDS.contains(type)){
                return KEYWORD;
        } else {
            return TokenType.BAD_CHARACTER;
        }
    }

    private static Tokens[] literals = {
            Tokens.tFloat,
            Tokens.tTrue,
            Tokens.tFalse,
            Tokens.tInteger
    };

    private static Tokens[] keywords = {

    };

    static {
        LITERALS = EnumSet.of(Tokens.tFloat,
                Tokens.tTrue,
                Tokens.tFalse,
                Tokens.tInteger);

        KEYWORDS = EnumSet.of(Tokens.tDef,
                Tokens.tDo,
                Tokens.tImplements,
                Tokens.tBEGIN,
                Tokens.tBegin,
                Tokens.tEND,
                Tokens.tClass,
                Tokens.tInterface,
                Tokens.tImport,
                Tokens.tDo,
                Tokens.tIf,
                Tokens.tImport,
                Tokens.tDefmacro,
                Tokens.tCase,
                Tokens.tElse,
                Tokens.tElsif,
                Tokens.tEnd,
                Tokens.tIn,
                Tokens.tRescue,
                Tokens.tRetry,
                Tokens.tReturn,
                Tokens.tBreak,
                Tokens.tClass,
                Tokens.tFalse,
                Tokens.tTrue,
                Tokens.tOr,
                Tokens.tAnd,
                Tokens.tUnless,
                Tokens.tUntil,
                Tokens.tFor,
                Tokens.tEnsure,
                Tokens.tDefined,
                Tokens.tNil,
                Tokens.tNot,
                Tokens.tPackage,
                Tokens.tRaise,
                Tokens.tMacro,
                Tokens.tRedo,
                Tokens.tSelf,
                Tokens.tSuper,
                Tokens.tACC_ABSTRACT,
                Tokens.tACC_PUBLIC,
                Tokens.tACC_PRIVATE,
                Tokens.tACC_PROTECTED,
                Tokens.tACC_FINAL,
                Tokens.tACC_VOLATILE,
                Tokens.tACC_NATIVE,
                Tokens.tACC_DEFAULT,
                Tokens.tACC_TRANSIENT
//                Tokens.tACC_SYNCHRONIZED
        );
    }
}
