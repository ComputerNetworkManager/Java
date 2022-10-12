package me.cnm.impl.shared.cli.component.system;

import lombok.RequiredArgsConstructor;
import me.cnm.shared.IHandlerLibrary;
import me.cnm.shared.cli.component.AbstractCLIComponent;
import me.cnm.shared.cli.component.system.ILoadingBar;
import me.cnm.shared.cli.log.LogLevel;
import me.cnm.shared.cli.message.ICLIMessageBuilder;
import me.cnm.shared.cli.message.create.CLIMessageBuilder;
import me.cnm.shared.cli.message.option.Color;
import me.cnm.shared.utility.IUtilityHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@RequiredArgsConstructor
public class LoadingBarComponent extends AbstractCLIComponent implements ILoadingBar {

    private final IHandlerLibrary handlerLibrary;
    private final String name;

    private double percentage;
    private boolean finished;

    @Override
    public void print(@NotNull LogLevel logLevel, @NotNull String message, @Nullable Throwable throwable) {
        if (this.finished) {
            this.getDefaultCLI().print(logLevel, message, throwable);
            return;
        }

        this.getDefaultCLI().printToConsole(CLIMessageBuilder.create().cursorPreviousLine().eraseLine());
        this.getDefaultCLI().print(logLevel, message, throwable);
        this.redraw(false);
    }

    @Override
    public @NotNull List<String> getAutoComplete(@NotNull String[] args) {
        return this.getDefaultCLI().getAutoComplete(args);
    }

    @Override
    public void handleInput(@NotNull String[] args) {
        this.getDefaultCLI().printToConsole(CLIMessageBuilder.create().cursorPreviousLine().eraseLine());
        this.getDefaultCLI().handleInput(args);
    }

    @Override
    public String getPrompt() {
        return this.getDefaultCLI().getPrompt();
    }

    @Override
    public void setPercentage(double percentage) {
        if (this.finished) return;

        if (percentage >= 100) {
            this.finish();
            return;
        }

        this.percentage = this.handlerLibrary.getHandler(IUtilityHandler.class)
                .getFormatHandler()
                .formatDouble(percentage, 1);

        this.redraw();
    }

    @Override
    public void finish() {
        if (this.finished) return;

        this.percentage = 100;
        this.finished = true;
        this.redraw();
    }

    @Override
    public boolean isFinished() {
        return this.finished;
    }

    private void redraw() {
        this.redraw(true);
    }

    private void redraw(boolean up) {
        ICLIMessageBuilder cliMessageBuilder = CLIMessageBuilder.create();
        if (up) cliMessageBuilder.cursorPreviousLine();

        this.getDefaultCLI().printlnToConsole(cliMessageBuilder
                .eraseLine()
                .text(getBar())
                .build());
    }

    private String getBar() {
        StringBuilder bar = new StringBuilder()
                .append(CLIMessageBuilder.create()
                        .textFg("[", Color.GRAY)
                        .build());

        for (int i = 0; i < 50; i++) {
            if ((i * 2) < percentage) bar.append(CLIMessageBuilder.create().textFg("=", Color.LIGHT_GREEN).build());
            else bar.append(" ");
        }

        bar.append(
                CLIMessageBuilder.create()
                        .textFg("]", Color.GRAY)
                        .fg(this.finished ? Color.LIGHT_GREEN : Color.LIGHT_RED)
                        .text(this.finished ? "Done" : this.percentage + "%")
                        .resetFormats()
                        .text(" " + this.name)
                        .build());

        return bar.toString();
    }

}
