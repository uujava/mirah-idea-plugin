package org.mirah.idea.plugin.psi;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.mirah.idea.plugin.MirahLanguage;

/**
 * Created by user on 5/20/2015.
 */
public class MirahElementType extends IElementType {
    public MirahElementType(@NotNull @NonNls String debugName) {
        super(debugName, MirahLanguage.INSTANCE);
    }

    public String toString(){
        return "mirah.element.type."+super.toString();
    }
}
