package org.mirah.idea.plugin.psi;

import com.intellij.lexer.LexerBase;
import com.intellij.psi.tree.IElementType;
import mirah.impl.MirahLexer;
import mirah.impl.MirahParser;
import mirah.impl.Tokens;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mirah.mmeta.BaseParser;

import java.lang.reflect.Field;

/**
 * Created by user on 5/20/2015.
 */
public class MirahHighlightingiLexer extends LexerBase {

    private MirahLexer mirahLexer;
    private CharSequenceInput input;
    private int currentPos;
    private BaseParser.Token<Tokens> currTok;
    private CharSequence charSequence;
    private int startPos;
    private int endPos;

    @Override
    public void start(@NotNull CharSequence charSequence, int start, int end, int state) {
        try {
            this.charSequence = charSequence;
            input = new CharSequenceInput(charSequence, 0, charSequence.length());
            mirahLexer = new MirahLexer(input);
            Field parser = mirahLexer.getClass().getDeclaredField("parser");
            parser.setAccessible(true);
            parser.set(mirahLexer, new MirahParser());
            this.startPos = start;
            this.currentPos = 0;
            this.currTok = null;
            this.endPos = end;
            System.out.println("MirahLexer: " + mirahLexer + " for psi lexer: " + System.identityHashCode(this) + " seq: " + System.identityHashCode(charSequence) + " start: " + start + " endPos: " + end);
            System.out.println("SEQUENCE:\n" + charSequence.subSequence(start, end).toString());
        } catch (Throwable e) {
            throw new RuntimeException("unable to create lexer for: " + charSequence + " start: " + start + " endPos " + end + " state " + state, e);
        }
    }

    @Override
    public int getState() {
        return 0;

    }

    @Override
    public String toString() {
        return new StringBuilder().append("MirahHighlightingiLexer: ")
                .append(System.identityHashCode(this))
                .append(" pos: ")
                .append(currentPos)
                .append(" tok: ")
                .append(currTok != null ? currTok.type : null).toString();
    }

    public MirahHighlightingiLexer() {
        System.out.println("created: " + this);
    }

    @Nullable
    @Override
    public IElementType getTokenType() {
        if (currentPos >= endPos) return null;
        if (currTok == null) _lex();
        if (currTok != null) {
            String txt = currTok.type == Tokens.tNL || currTok.type == Tokens.tWhitespace ? currTok.type.toString() : charSequence.subSequence(currTok.startpos, currTok.endpos).toString();
            System.out.println("token = " + txt + " [" + currTok.startpos + "," + currTok.endpos + "]:" + currTok.type);
            return MirahTokenType.mapFromMirah(currTok.type);
        } else {
            System.out.println("null token");
            return null;
        }
    }

    @Override
    public int getTokenStart() {
        return currTok.startpos;
    }

    @Override
    public int getTokenEnd() {
        if (currTok == null) _lex();
        return currTok.endpos;
    }

    @Override
    public void advance() {
        if (currTok == null) _lex();
        currTok = null;
    }

    @NotNull
    @Override
    public CharSequence getBufferSequence() {
        return this.charSequence;
    }

    @Override
    public int getBufferEnd() {
        return endPos;
    }


    private void _lex() {
        if (currentPos >= endPos) {
            currTok = null;
            currentPos = endPos;
            return;
        }
        if (currentPos < startPos) {
            while (currentPos < startPos) {
                currTok = mirahLexer.lex(currentPos, false);
                currentPos = input.pos();
            }
            if (currTok.startpos < startPos) {
                currTok = mirahLexer.lex(currentPos, false);
                currentPos = input.pos();
            }
        } else {
            currTok = mirahLexer.lex(currentPos, false);
            currentPos = currTok.endpos;
        }
    }


}
