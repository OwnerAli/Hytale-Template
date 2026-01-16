package me.alii;

import com.hypixel.hytale.server.core.command.system.CommandRegistry;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.util.Config;
import me.alii.commands.FirstCommand;
import me.alii.config.BlockBreakConfig;
import me.alii.systems.BlockBreakEventSystem;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class FirstPlugin extends JavaPlugin {
    private final Config<BlockBreakConfig> config;

    public FirstPlugin(@NonNullDecl JavaPluginInit init) {
        super(init);
        this.config = this.withConfig("BlockBreakConfig", BlockBreakConfig.CODEC);
    }

    @Override
    protected void setup() {
        // Save Config
        this.config.save();

        // Initialize Commands
        CommandRegistry commandRegistry = this.getCommandRegistry();
        commandRegistry.registerCommand(new FirstCommand());

        // Initialize Event Systems
        this.getEntityStoreRegistry().registerSystem(new BlockBreakEventSystem(config));
    }
}