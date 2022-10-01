package me.cnm.shared.cli.component.system;

import me.cnm.shared.cli.component.ICLIComponent;

/**
 * The {@code LoadingBar} is used for loading bars in the console
 */
public interface ILoadingBar extends ICLIComponent {

    /**
     * Set the current percentage (0.0 - 100.0) of the loading bar<br>
     * The value will be rounded to 1 decimal digit<br>
     * If the value is >= 100, {@link #finish()} is called
     *
     * @param percentage The percentage of the loading bar
     */
    void setPercentage(double percentage);

    /**
     * Mark the {@code LoadingBar} as finished, {@link #setPercentage(double)} won't have any effect any longer
     */
    void finish();

    /**
     * Check if the {@code LoadingBar} has already finished loading
     * @return Whether the {@code LoadingBar} is finished
     */
    boolean isFinished();

}
