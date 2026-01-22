package me.alii.interaction;

import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.BlockPosition;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.InteractionContext;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.inventory.transaction.ItemStackSlotTransaction;
import com.hypixel.hytale.server.core.modules.block.BlockModule;
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInteraction;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.meta.BlockState;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import me.alii.interactive.OrganizedChestState;
import me.alii.pages.ChestFilterPage;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import java.awt.*;

@SuppressWarnings("removal")
public class ConfigureChestInteraction extends SimpleInteraction {

    public static final BuilderCodec<ConfigureChestInteraction> CODEC =
            BuilderCodec.builder(ConfigureChestInteraction.class, ConfigureChestInteraction::new,
                            SimpleInteraction.CODEC)
                    .build();

    @Override
    protected void tick0(boolean firstRun,
                         float time,
                         @NonNullDecl InteractionType type,
                         @NonNullDecl InteractionContext context,
                         @NonNullDecl CooldownHandler cooldownHandler) {
        Ref<EntityStore> owningEntity = context.getOwningEntity();
        Store<EntityStore> store = owningEntity.getStore();
        Player player = store.getComponent(owningEntity, Player.getComponentType());

        World world = player.getWorld();
        if (world == null) return;

        BlockPosition targetBlock = context.getTargetBlock();
        if (targetBlock == null) return;

        ItemStack heldItem = context.getHeldItem();
        if (heldItem == null) return;
        if (!heldItem.getItem().getId().equalsIgnoreCase("Ore_Iron")) return;
        int requiredAmount = 2;

        ItemStackSlotTransaction itemStackSlotTransaction = player.getInventory().getHotbar()
                .removeItemStackFromSlot(context.getHeldItemSlot(), requiredAmount, true, false);
        if (!itemStackSlotTransaction.succeeded()) return;

        Ref<ChunkStore> blockEntity = BlockModule.getBlockEntity(world,
                targetBlock.x, targetBlock.y, targetBlock.z);
        Store<ChunkStore> chunkStoreStore = blockEntity.getStore();
        BlockState blockState = BlockState.getBlockState(blockEntity, chunkStoreStore);
        if (!(blockState instanceof OrganizedChestState chestState)) return;
        chestState.setupSlots(OrganizedChestState.SlotFilter.builder()
                .filterStrings(new String[]{"Ore[0-2]"})
                .build());
        player.sendMessage(Message.raw("Updating Config...").color(Color.ORANGE).bold(true));
        player.getPageManager().openCustomPage(owningEntity, store, new ChestFilterPage(player.getPlayerRef(), chestState));
    }
}