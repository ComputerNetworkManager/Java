package me.cnm.impl.shared.module.loading;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.cnm.shared.module.IModuleDescription;
import me.cnm.shared.module.loading.IModule;

import java.io.File;

@Getter
@RequiredArgsConstructor
public class Module implements IModule {

    private final IModuleDescription moduleDescription;
    private final File dataFolder;

    @Setter
    private boolean running;

}
