package me.alii;

import com.hypixel.hytale.server.core.command.system.CommandRegistry;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.world.meta.BlockStateRegistry;
import com.hypixel.hytale.server.core.universe.world.meta.state.ItemContainerState;
import com.hypixel.hytale.server.core.util.Config;
import me.alii.commands.FirstCommand;
import me.alii.config.BlockBreakConfig;
import me.alii.interactions.ConfigureChestFilterInteraction;
import me.alii.states.FilterChestState;
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

        // Register new block state
        BlockStateRegistry blockStateRegistry = this.getBlockStateRegistry();
        blockStateRegistry.registerBlockState(FilterChestState.class, "FilterChestState",
                FilterChestState.CODEC, ItemContainerState.ItemContainerStateData.class, ItemContainerState.ItemContainerStateData.CODEC);

        // Register Interaction Codec
        this.getCodecRegistry(Interaction.CODEC)
                .register("ConfigureChestFilter", ConfigureChestFilterInteraction.class, ConfigureChestFilterInteraction.CODEC);
    }
}