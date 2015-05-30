package org.mirah.idea.plugin;

import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by user on 5/20/2015.
 */
public class MirahFileType extends LanguageFileType {

    public static final MirahFileType INSTANCE = new MirahFileType();

    public MirahFileType() {
        super(MirahLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "Mirah";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Mirah source file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "mirah";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return MirahIcons.FILE;
    }
}
