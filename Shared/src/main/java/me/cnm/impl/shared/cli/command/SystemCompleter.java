package me.cnm.impl.shared.cli.command;

import lombok.RequiredArgsConstructor;
import me.cnm.shared.cli.component.ICLIComponent;
import org.jline.reader.Candidate;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;

import java.util.List;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class SystemCompleter implements Completer {

    private final Supplier<ICLIComponent> componentSupplier;

    @Override
    public void complete(LineReader lineReader, ParsedLine parsedLine, List<Candidate> list) {
        String[] args = parsedLine.line().split(" ");
        List<String> candidates = componentSupplier.get().getAutoComplete(args);

        for (String candidate : candidates) list.add(new Candidate(candidate));
    }

}
