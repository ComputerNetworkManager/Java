package me.cnm.shared.cli.component;

import lombok.NonNull;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;

/**
 * The {@code AbstractCLIComponent} is an extension of the {@link ICLIComponent}<br>
 * If the component is set, the {@code defaultCLI} is automatically initialized
 * To create a custom CLI component a class must be created and extended by {@code AbstractCLIComponent}
 */
public abstract class AbstractCLIComponent implements ICLIComponent {

    /**
     * The object to store the {@link IDefaultCLI} object
     */
    private IDefaultCLI defaultCLI;

    /**
     * <strong>Only for internal use!</strong><br>
     * <br>
     * Set the {@code defaultCLI}<br>
     * Is done before setting to component as cli component<br>
     *
     * @param defaultCLI The {@code defaultCLI} to set
     */
    @ApiStatus.Internal
    public void setDefaultCLI(@NonNull IDefaultCLI defaultCLI) {
        this.defaultCLI = defaultCLI;
    }

    /**
     * Get the {@code defaultCLI}<br>
     * The {@code defaultCLI} is used to access deeper actions and components like the console print stream
     *
     * @return The {@code defaultCLI}
     * @see IDefaultCLI
     */
    @ApiStatus.NonExtendable
    @Contract(pure = true)
    protected IDefaultCLI getDefaultCLI() {
        return defaultCLI;
    }

}
