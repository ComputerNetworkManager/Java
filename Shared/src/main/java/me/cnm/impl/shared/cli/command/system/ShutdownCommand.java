package me.cnm.impl.shared.cli.command.system;

import me.cnm.shared.cli.command.Command;
import me.cnm.shared.cli.log.ILogHandler;
import org.fusesource.jansi.Ansi;
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
            this.getHandlerLibrary().getHandler(ILogHandler.class).info("Use " +
                    Ansi.ansi().fgBrightCyan().a("shutdown").reset());
            return;
        }

        long now = System.currentTimeMillis();
        if (lastExecute + TimeUnit.SECONDS.toMillis(30) >= now) {
            this.getHandlerLibrary().getHandler(ILogHandler.class).info("The system will now shutdown.");
            System.exit(0);
        }

        this.getHandlerLibrary().getHandler(ILogHandler.class).info("If you want the system to shutdown, " +
                "enter this command again in the next "
                + Ansi.ansi().fgBrightCyan().a("30 seconds").reset() + ".");
        this.lastExecute = System.currentTimeMillis();
    }

    @Override
    public String getDescription() {
        return "Shuts the system down";
    }

}
