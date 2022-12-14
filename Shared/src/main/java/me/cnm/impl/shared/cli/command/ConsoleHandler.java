package me.cnm.impl.shared.cli.command;

import lombok.Setter;
import me.cnm.impl.shared.cli.log.PrintAboveAppender;
import me.cnm.shared.cli.component.ICLIComponent;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class ConsoleHandler {

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    private final Supplier<ICLIComponent> componentSupplier;
    private final LineReader lineReader;

    @Setter
    private String suggestion;

    private boolean shouldInterrupt = false;
    private boolean run = true;
    private Thread thread;

    public ConsoleHandler(Supplier<ICLIComponent> componentSupplier) {
        this.componentSupplier = componentSupplier;

        this.lineReader = LineReaderBuilder.builder()
                .completer(new SystemCompleter(componentSupplier))
                .build();

        PrintAboveAppender.setLineReader(this.lineReader);
    }

    public void startListen() {
        this.executorService.submit(() -> {
            while (this.run) {
                this.thread = new Thread(this::runConsole);

                this.thread.start();
                try {
                    this.thread.join();
                } catch (InterruptedException e) {
                    this.thread.interrupt();
                }
            }
        });
    }

    private void runConsole() {
        String readLine;
        try {
            while ((readLine = this.lineReader.readLine("\r" +
                    this.componentSupplier.get().getPrompt(), null, this.suggestion)) != null) {
                this.suggestion = null;
                this.componentSupplier.get().handleInput(readLine.split(" "));
            }
        } catch (UserInterruptException e) {
            if (!this.shouldInterrupt) System.exit(0);
        }
    }

    public void interrupt() {
        if (this.thread != null) {
            this.shouldInterrupt = true;
            this.thread.interrupt();
        }
    }

    public void stop() {
        this.run = false;
        this.interrupt();
    }

    public void printAbove(Object object) {
        this.lineReader.printAbove(object.toString());
    }
}
