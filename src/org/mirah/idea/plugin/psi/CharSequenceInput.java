package org.mirah.idea.plugin.psi;

import mirah.impl.MirahLexer;

/**
 * Created by user on 5/20/2015.
 */
public class CharSequenceInput implements MirahLexer.Input {

    private static final int EOF = -1;
    private int pos = 0;
    private final CharSequence string;
    private final int end;

    public CharSequenceInput(CharSequence string, int start, int end) {
        this.string = string;
        this.pos = start;
        this.end = end;
    }

    public int pos() {
        return pos;
    }

    public int read() {
        if (pos >= end) {
            pos = end + 1;
            return EOF;
        }
        return string.charAt(pos++);
    }

    public boolean consume(char c) {
        if (read() == c) {
            return true;
        }
        --pos;
        return false;
    }

    public boolean consume(String s) {
        boolean contains = false;
        for (int i = 0; i < s.length() && pos + i < string.length(); i++) {
            if (string.charAt(pos + i) == s.charAt(i)) {
                contains = true;
            } else {
                contains = false;
                break;
            }
        }
        if (contains) {
            pos += s.length();
            return true;
        }
        return false;
    }

    public void backup(int amount) {
        pos -= amount;
    }

    public void skip(int amount) {
        if (pos < end - amount) {
            pos += amount;
        } else {
            pos = end;
        }
    }

    public boolean hasNext() {
        return pos < end;
    }

    public void consumeLine() {
        int i = pos;
        for (; i < string.length(); i++) {
            if (string.charAt(i) == '\n') {
                pos = i;
                break;
            }
        }
        pos = (pos != i) ? end : pos;
    }

    public int peek() {
        int result = read();
        --pos;
        return result;
    }

    public int finishCodepoint() {
        int size = 1;
        if (pos < end - 1) {
            ++pos;
            ++size;
        }
        return Character.codePointAt(string, pos - size);
    }

    public CharSequence readBack(int length) {
        return string.subSequence(pos - length, pos);
    }
}
