package me.cnm.shared.utility;

import me.cnm.shared.utility.mapping.ITypeMapper;
import org.jetbrains.annotations.NotNull;

/**
 * Class to expose utility resources
 */
public interface IUtilityHandler {

    /**
     * Get the type mapper
     * @return the type mapper
     * @see ITypeMapper
     */
    @NotNull
    ITypeMapper getTypeMapper();

}
