package me.cnm.shared.utility.scope.fi;

/**
 * A {@link Runnable} that can throw any exception
 *
 * @see Runnable
 */
@FunctionalInterface
public interface ThrowRunnable {

    /**
     * The run method of the runnable
     *
     * @throws Exception Can be thrown
     * @see Runnable#run()
     */
    void run() throws Exception;

}
