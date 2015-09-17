package org.mirah.idea.plugin.psi;

import com.intellij.openapi.diagnostic.Logger;
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
public class MirahHighlightingLexer extends LexerBase {
    private final static Logger LOG = Logger.getInstance(MirahHighlightingLexer.class);
    private MirahLexer mirahLexer;
    private CharSequenceInput input;
    private int currentPos;
    private BaseParser.Token<Tokens> currTok;
    private CharSequence charSequence;
    private int startPos;
    private int endPos;
    private int lastStart;
    private int lastEnd;

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
            this.endPos = end > charSequence.length()? charSequence.length() : end;
            lastStart = lastEnd = start;
            if(LOG.isDebugEnabled()) LOG.debug("MirahLexer: " + mirahLexer + " for psi lexer: " + System.identityHashCode(this) + " seq: " + System.identityHashCode(charSequence) + " start: " + start + " endPos: " + end);
            if(LOG.isDebugEnabled()) LOG.debug("SEQUENCE:\n" + charSequence.subSequence(this.startPos, this.endPos).toString());
            advance();
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

    public MirahHighlightingLexer() {
        if(LOG.isDebugEnabled()) LOG.debug("created: " + this);
    }

    @Nullable
    @Override
    public IElementType getTokenType() {
        if (currTok != null) {
            String txt = currTok.type == Tokens.tNL || currTok.type == Tokens.tWhitespace ? currTok.type.toString() : charSequence.subSequence(currTok.startpos, currTok.endpos).toString();
            if(LOG.isDebugEnabled()) LOG.debug("token = " + txt + " [" + currTok.startpos + "," + currTok.endpos + "]:" + currTok.type);
            return MirahTokenType.mapFromMirah(currTok.type);
        } else {
            if(LOG.isDebugEnabled()) LOG.debug("null token");
            return null;
        }
    }

    @Override
    public int getTokenStart() {
        return lastStart;
    }

    @Override
    public int getTokenEnd() {
        return lastEnd;
    }

    @Override
    public void advance() {
        if (currentPos >= endPos) {
            currTok = null;
            lastEnd = currentPos = endPos;
            return;
        }
        if (currentPos < startPos) {
            while (currentPos < startPos) {
                _lex();
            }
            if (currTok.startpos < startPos) {
                _lex();
            }
        } else {
            _lex();
        }
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

    private void _lex(){
        try{
            currTok = mirahLexer.lex(currentPos, false);
            currentPos = input.pos();
            lastEnd = currTok.endpos;
            lastStart = currTok.startpos;
        }catch (Exception ex) {
            if(LOG.isDebugEnabled()) LOG.debug("bad character. currentPos: " + currentPos + " currTok" + currTok);
            currTok = null;
            lastStart = lastEnd;
            lastEnd = currentPos = endPos;
        }
    }

}
