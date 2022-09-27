package me.cnm.impl.shared.utility;

import me.cnm.impl.shared.utility.mapping.TypeMapper;
import me.cnm.shared.utility.IUtilityHandler;
import me.cnm.shared.utility.mapping.ITypeMapper;
import org.jetbrains.annotations.NotNull;

public class UtilityHandler implements IUtilityHandler {

    private final ITypeMapper typeMapper;

    public UtilityHandler() {
        typeMapper = new TypeMapper();
    }

    @Override
    public @NotNull ITypeMapper getTypeMapper() {
        return this.typeMapper;
    }

}
