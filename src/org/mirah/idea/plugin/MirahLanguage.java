package org.mirah.idea.plugin;

import com.intellij.lang.Language;

import javax.management.loading.MLet;

/**
 * Created by user on 5/20/2015.
 */
public class MirahLanguage extends Language {

    public static final Language INSTANCE = new MirahLanguage();

    protected MirahLanguage() {
        super("mirah");
    }
}
