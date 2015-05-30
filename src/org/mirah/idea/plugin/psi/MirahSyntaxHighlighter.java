package org.mirah.idea.plugin.psi;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import static org.mirah.idea.plugin.psi.MirahTokenType.*;
/**
 * Created by kozyr on 21.05.2015.
 */
public class MirahSyntaxHighlighter implements SyntaxHighlighter {

    private static final Map<IElementType, TextAttributesKey> KEYS;
    
    static {
        KEYS = new HashMap<IElementType, TextAttributesKey>();
        KEYS.put(KEYWORD, DefaultLanguageHighlighterColors.KEYWORD);
        KEYS.put(IDENTIFIER, DefaultLanguageHighlighterColors.IDENTIFIER);
        KEYS.put(LITERAL, DefaultLanguageHighlighterColors.NUMBER);
        KEYS.put(CHARACTER, DefaultLanguageHighlighterColors.STRING);
        KEYS.put(INSTANCE_VAR, DefaultLanguageHighlighterColors.INSTANCE_FIELD);
        KEYS.put(CLASS_VAR, DefaultLanguageHighlighterColors.STATIC_FIELD);
        KEYS.put(STRING, DefaultLanguageHighlighterColors.STRING);
        KEYS.put(COMMENT, DefaultLanguageHighlighterColors.LINE_COMMENT);
        KEYS.put(CONSTANT, DefaultLanguageHighlighterColors.CONSTANT);
        KEYS.put(JAVA_COMMENT, DefaultLanguageHighlighterColors.BLOCK_COMMENT);
//        KEYS.put(WHITESPACE, DefaultLanguageHighlighterColors.BLOCK_COMMENT);

        KEYS.put(UNKNOWN, HighlighterColors.BAD_CHARACTER);
    }
    

    @NotNull
    public Lexer getHighlightingLexer() {
        return new MirahHighlightingiLexer();
    }

    @NotNull
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        return SyntaxHighlighterBase.pack(KEYS.get(tokenType));
    }

}
