package me.cnm.impl.shared.cli.command.system;

import me.cnm.shared.cli.command.Command;
import me.cnm.shared.cli.command.ICommandHandler;
import me.cnm.shared.cli.log.ILogHandler;
import me.cnm.shared.utility.scope.Scopes;
import org.fusesource.jansi.Ansi;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

public class HelpCommand extends Command {

    public HelpCommand() {
        super("help");
    }

    @Override
    public void execute(@NotNull String[] args) {
        if (args.length != 0) {
            this.getHandlerLibrary().getHandler(ILogHandler.class).info("Use " +
                    Ansi.ansi().fgBrightCyan().a("help").reset());
            return;
        }

        this.getHandlerLibrary().getHandler(ILogHandler.class).info("--- " +
                Ansi.ansi().fgBrightCyan().a("Commands").reset() + " ---");


        for (Command command : this.getHandlerLibrary().getHandler(ICommandHandler.class).getAll()
                .stream()
                .sorted(Comparator.comparing(Command::getName))
                .toList()) {
            String aliases = String.join(", ", command.getAliases());
            String description = Scopes.or(command.getDescription(), "");
            String syntax = Scopes.or(command.getSyntax(), "");

            boolean hasAliases = !aliases.equals("");
            boolean hasDescription = !description.equals("");
            boolean hasSyntax = !syntax.equals("");

            // shutdown <help> (Shuts the system down), [exit, end, stop, ...]
            this.getHandlerLibrary().getHandler(ILogHandler.class).info(String.format(
                    Ansi.ansi()
                            .fgBrightCyan().a("%s")
                            .reset().a("%s")
                            .fgCyan().a("%s")
                            .reset().a("%s")
                            .fgBrightBlack().a("%s")
                            .reset().a("%s")
                            .fgCyan().a("%s")
                            .reset().a("%s")
                            .toString(),
                    command.getName(), (hasSyntax ? " " : ""), syntax, (hasDescription ? " (" : ""), description,
                    (hasDescription ? ")" : "") + (hasAliases ? ", [" : ""), aliases, (hasAliases ? "]" : "")
            ));
        }

    }

    @Override
    public String getDescription() {
        return "Prints this message";
    }
}
