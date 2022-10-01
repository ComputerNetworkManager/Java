package me.cnm.impl.shared.cli.command;

import me.cnm.shared.cli.component.ICLIComponent;
import org.fusesource.jansi.Ansi;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.reader.impl.LineReaderImpl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class ConsoleHandler {

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    private final Supplier<ICLIComponent> componentSupplier;
    private final LineReader lineReader;

    public ConsoleHandler(Supplier<ICLIComponent> componentSupplier) {
        this.componentSupplier = componentSupplier;

        this.lineReader = LineReaderBuilder.builder()
                .completer(new SystemCompleter(componentSupplier))
                .build();
    }

    public void startListen() {
        this.executorService.submit(() -> {
            String readLine;
            try {
                while ((readLine = this.lineReader.readLine(Ansi.ansi()
                        .fgBrightRed().a("CNM")
                        .reset().a(">")
                        .toString())) != null) {
                    componentSupplier.get().handleInput(readLine.split(" "));
                }
            } catch (UserInterruptException e) {
                System.exit(0);
            }
        });
    }

    public void redrawLine() {
        ((LineReaderImpl) this.lineReader).redrawLine();
        ((LineReaderImpl) this.lineReader).redisplay();
    }

}
