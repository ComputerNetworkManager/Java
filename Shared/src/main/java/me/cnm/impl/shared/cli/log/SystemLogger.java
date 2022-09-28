package me.cnm.impl.shared.cli.log;

import lombok.NonNull;
import me.cnm.shared.cli.log.LogLevel;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class SystemLogger {


    private final Logger logger = LogManager.getLogger("ComputerNetworkManager");

    public void log(@NonNull LogLevel logLevel, @NonNull String message, Throwable throwable) {

    }

}
