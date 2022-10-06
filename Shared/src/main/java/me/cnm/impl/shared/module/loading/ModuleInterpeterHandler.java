package me.cnm.impl.shared.module.loading;

import lombok.NonNull;
import me.cnm.shared.module.loading.IModuleInterpreter;
import me.cnm.shared.utility.helper.ArrayHelper;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ModuleInterpeterHandler {

    private final List<ModuleInterpreterHolder> moduleInterpreters = new ArrayList<>();

    public void registerInterpreter(@NonNull String language, @NonNull IModuleInterpreter interpreter, @NonNull String... aliases) {
        for (ModuleInterpreterHolder moduleInterpreter : this.moduleInterpreters) {
            if (ArrayHelper.containsIgnoreCase(moduleInterpreter.getLanguages().toArray(String[]::new), language) ||
                    ArrayHelper.containsIgnoreCase(moduleInterpreter.getLanguages().toArray(String[]::new), aliases))
                throw new IllegalStateException("An module interpreter for" + language + "/" +
                        String.join(", ", aliases) + "already exists.");
        }

        this.moduleInterpreters.add(new ModuleInterpreterHolder(interpreter,
                Stream.of(new String[] { language}, aliases)
                        .flatMap(Stream::of)
                        .toList()));
    }

    @Nullable
    public IModuleInterpreter getInterpreter(@NonNull String language) {
        for (ModuleInterpreterHolder holder : this.moduleInterpreters) {
            if (ArrayHelper.containsIgnoreCase(holder.getLanguages().toArray(String[]::new), language))
                return holder.getModuleInterpreter();
        }

        return null;
    }

}
