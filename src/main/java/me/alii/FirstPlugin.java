package me.alii;

import com.hypixel.hytale.component.ComponentRegistryProxy;
import com.hypixel.hytale.server.core.command.system.CommandRegistry;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.world.meta.state.ItemContainerState;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.Config;
import lombok.Getter;
import me.alii.commands.FirstCommand;
import me.alii.config.BlockBreakConfig;
import me.alii.interaction.ConfigureChestInteraction;
import me.alii.interactive.OrganizedChestState;
import me.alii.registry.ChestFilterRegistry;
import me.alii.systems.BlockBreakEventSystem;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

@Getter
public class FirstPlugin extends JavaPlugin {
    private final Config<BlockBreakConfig> config;
    private final ChestFilterRegistry chestFilterRegistry;

    @Getter
    public static FirstPlugin instance;

    public FirstPlugin(@NonNullDecl JavaPluginInit init) {
        super(init);
        this.config = this.withConfig("BlockBreakConfig", BlockBreakConfig.CODEC);
        this.chestFilterRegistry = new ChestFilterRegistry();
    }

    @Override
    protected void setup() {
        // Save Config
        this.config.save();

        // Initialize Commands
        CommandRegistry commandRegistry = this.getCommandRegistry();
        commandRegistry.registerCommand(new FirstCommand());

        // Initialize Event Systems
        ComponentRegistryProxy<EntityStore> entityStoreRegistry = this.getEntityStoreRegistry();
        entityStoreRegistry.registerSystem(new BlockBreakEventSystem(config));

        // Register new block state
        this.getBlockStateRegistry().registerBlockState(OrganizedChestState.class, "Alii_FirstPlugin_OrgChst",
                OrganizedChestState.CODEC, ItemContainerState.ItemContainerStateData.class, ItemContainerState.ItemContainerStateData.CODEC);

        // Register codec
        this.getCodecRegistry(Interaction.CODEC)
                .register("ConfigureChestFilter", ConfigureChestInteraction.class, ConfigureChestInteraction.CODEC);
    }
}