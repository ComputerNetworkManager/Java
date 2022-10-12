package me.cnm.impl.shared.cli.command.system;

import me.cnm.shared.cli.command.Command;
import me.cnm.shared.cli.log.ILogHandler;
import me.cnm.shared.cli.message.create.CLIMessageBuilder;
import me.cnm.shared.cli.message.option.Color;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class ShutdownCommand extends Command {

    private long lastExecute = 0;

    public ShutdownCommand() {
        super("shutdown", "exit", "stop", "end");
    }

    @Override
    public void execute(@NotNull String[] args) {
        if (args.length != 0) {
            this.getHandlerLibrary().getHandler(ILogHandler.class).info(
                    CLIMessageBuilder.create()
                            .text("Use ")
                            .textFg("shutdown", Color.LIGHT_CYAN)
                            .build());
            return;
        }

        long now = System.currentTimeMillis();
        if (lastExecute + TimeUnit.SECONDS.toMillis(30) >= now) {
            this.getHandlerLibrary().getHandler(ILogHandler.class).info("The system will now shutdown.");
            System.exit(0);
        }

        this.getHandlerLibrary().getHandler(ILogHandler.class).info(
                CLIMessageBuilder.create()
                        .text("If you want the system to shutdown, enter this command again in the next ")
                        .textFg("30 seconds", Color.LIGHT_CYAN)
                        .text(".")
                        .build());
        this.lastExecute = System.currentTimeMillis();
    }

    @Override
    public String getDescription() {
        return "Shuts the system down";
    }

}
