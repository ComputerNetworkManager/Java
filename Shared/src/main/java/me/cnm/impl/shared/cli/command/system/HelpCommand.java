package me.cnm.impl.shared.cli.command.system;

import me.cnm.shared.cli.command.Command;
import me.cnm.shared.cli.command.ICommandHandler;
import me.cnm.shared.cli.log.ILogHandler;
import me.cnm.shared.cli.message.create.CLIMessageBuilder;
import me.cnm.shared.cli.message.option.Color;
import me.cnm.shared.utility.scope.Scopes;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

public class HelpCommand extends Command {

    public HelpCommand() {
        super("help");
    }

    @Override
    public void execute(@NotNull String[] args) {
        if (args.length != 0) {
            this.getHandlerLibrary().getHandler(ILogHandler.class).info(
                    CLIMessageBuilder.create()
                            .text("Use ")
                            .textFg("help", Color.LIGHT_CYAN)
                            .build());
            return;
        }

        this.getHandlerLibrary().getHandler(ILogHandler.class).info(
                CLIMessageBuilder.create()
                        .text("----- ")
                        .textFg("Commands", Color.LIGHT_CYAN)
                        .text(" -----")
                        .build());


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
                    CLIMessageBuilder.create()
                            .textFg("%s", Color.LIGHT_CYAN)
                            .text("%s")
                            .textFg("%s", Color.CYAN)
                            .text("%s")
                            .textFg("%s", Color.GRAY)
                            .text("%s")
                            .textFg("%s", Color.CYAN)
                            .text("%s")
                            .build(),
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
