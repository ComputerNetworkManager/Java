package me.cnm.impl.shared.cli.component.system;

import lombok.RequiredArgsConstructor;
import me.cnm.shared.IHandlerLibrary;
import me.cnm.shared.cli.component.AbstractCLIComponent;
import me.cnm.shared.cli.component.system.ILoadingBar;
import me.cnm.shared.cli.log.LogLevel;
import me.cnm.shared.utility.IUtilityHandler;
import org.fusesource.jansi.Ansi;
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

        this.getDefaultCLI().printToConsole(Ansi.ansi().cursorUpLine().eraseLine());
        this.getDefaultCLI().print(logLevel, message, throwable);
        this.redraw(false);
    }

    @Override
    public @NotNull List<String> getAutoComplete(@NotNull String[] args) {
        return this.getDefaultCLI().getAutoComplete(args);
    }

    @Override
    public void handleInput(@NotNull String[] args) {
        this.getDefaultCLI().handleInput(args);
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
        Ansi ansi = Ansi.ansi();
        if (up) ansi = ansi.cursorUpLine();

        this.getDefaultCLI().printlnToConsole(ansi
                .eraseLine()
                .a(getBar())
                .toString());
    }

    private String getBar() {
        StringBuilder bar = new StringBuilder()
                .append(Ansi.ansi()
                        .fgBrightBlack()
                        .a("["));

        for (int i = 0; i < 50; i++) {
            if ((i * 2) < percentage) bar.append(Ansi.ansi().fgBrightGreen().a("="));
            else bar.append(" ");
        }

        bar.append(Ansi.ansi().fgBrightBlack()
                .a("] ")
                .fgBright(this.finished ? Ansi.Color.GREEN : Ansi.Color.RED)
                .a(this.finished ? "Done" : this.percentage + "%")
                .reset()
                .a(" ")
                .a(this.name));

        return bar.toString();
    }

}
