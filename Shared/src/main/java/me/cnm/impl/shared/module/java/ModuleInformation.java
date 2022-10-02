package me.cnm.impl.shared.module.java;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import me.cnm.shared.module.java.JavaModule;

@Data
@RequiredArgsConstructor
public class ModuleInformation {

    private final ModuleClassLoader classLoader;
    private final Class<? extends JavaModule> mainClass;
    private JavaModule mainInstance;

}
