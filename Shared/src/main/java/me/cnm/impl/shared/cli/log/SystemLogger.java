package me.cnm.impl.shared.cli.log;

import lombok.NonNull;
import me.cnm.shared.IHandlerLibrary;
import me.cnm.shared.cli.log.LogLevel;
import me.cnm.shared.utility.configuration.IConfigurationHandler;
import me.cnm.shared.utility.json.JsonDocument;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.*;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.jetbrains.annotations.NotNull;

public class SystemLogger {

    private final IHandlerLibrary handlerLibrary;
    private final Logger logger;

    public SystemLogger(IHandlerLibrary handlerLibrary) {
        this.handlerLibrary = handlerLibrary;

        this.configureLogger();
        this.logger = LogManager.getRootLogger();
    }

    public void log(@NonNull LogLevel logLevel, @NonNull String message, Throwable throwable) {
        this.logger.log(this.toLog4JLevel(logLevel), message, throwable);
    }

    @SuppressWarnings("java:S1192")
    private void configureLogger() {
        // Load config
        JsonDocument defaultFileDocument = new JsonDocument()
                .append("pattern", "%date{HH:mm:ss.SSS} | %-5level | %replace{%message}{\u001B\\d*(;\\d+)[a-z]}{}%n")
                .append("fileName", "log/latest.log")
                .append("filePattern", "log/%date{yyyy-MM-dd}.log");

        JsonDocument configuration = this.handlerLibrary.getHandler(IConfigurationHandler.class)
                .getEntry("logger", new JsonDocument()
                                .append("level", LogLevel.INFO)
                                .append("console", "%date{HH:mm:ss.SSS} | %-5level | %message%n")
                                .append("file", defaultFileDocument),
                        JsonDocument.class);

        JsonDocument fileDocument = configuration.getDocument("file", defaultFileDocument);

        Level logLevel = this.toLog4JLevel(configuration.get("level", LogLevel.DEBUG, LogLevel.class));

        // Create builder
        ConfigurationBuilder<BuiltConfiguration> builder = ConfigurationBuilderFactory.newConfigurationBuilder();
        builder.setStatusLevel(logLevel);

        // Layout
        LayoutComponentBuilder consoleLayoutBuilder = builder.newLayout("PatternLayout")
                .addAttribute("pattern", configuration.getString("console"));

        LayoutComponentBuilder fileLayoutBuilder = builder.newLayout("PatternLayout")
                .addAttribute("pattern", fileDocument.getString("pattern"));

        // Console
        AppenderComponentBuilder consoleComponent = builder.newAppender("Console", "Console")
                .addAttribute("target", ConsoleAppender.Target.SYSTEM_OUT)
                .add(consoleLayoutBuilder);

        // File
        ComponentBuilder<?> triggerPolicy = builder.newComponent("Policies")
                .addComponent(builder.newComponent("TimeBasedTriggeringPolicy")
                        .addAttribute("interval", 1)
                        .addAttribute("modulate", true));

        AppenderComponentBuilder rollingFile = builder.newAppender("RollingFile", "RollingFile")
                .addAttribute("fileName", fileDocument.getString("fileName", "log/latest.log"))
                .addAttribute("filePattern", fileDocument.getString("filePattern", "log/%{yyyy-MM-dd}.log"))
                .add(fileLayoutBuilder)
                .addComponent(triggerPolicy);

        // Configure
        RootLoggerComponentBuilder rootLogger = builder.newRootLogger(logLevel);

        rootLogger.add(builder.newAppenderRef("Console"));
        rootLogger.add(builder.newAppenderRef("RollingFile"));

        builder.add(consoleComponent);
        builder.add(rollingFile);
        builder.add(rootLogger);

        Configurator.reconfigure(builder.build());
    }

    @NotNull
    private Level toLog4JLevel(@NonNull LogLevel logLevel) {
        return switch (logLevel) {
            case DEBUG -> Level.DEBUG;
            case INFO -> Level.INFO;
            case WARN -> Level.WARN;
            case ERROR -> Level.ERROR;
        };
    }

}
