package me.cnm.impl.shared.module.loading;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import me.cnm.shared.module.loading.IModuleInterpreter;

import java.util.List;

@Data
@RequiredArgsConstructor
public class ModuleInterpreterHolder {

    private final IModuleInterpreter moduleInterpreter;
    private final List<String> languages;

}
