package me.alii.pages;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime;
import com.hypixel.hytale.protocol.packets.interface_.CustomUIEventBindingType;
import com.hypixel.hytale.protocol.packets.interface_.Page;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.pages.InteractiveCustomUIPage;
import com.hypixel.hytale.server.core.ui.builder.EventData;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.ui.builder.UIEventBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import me.alii.interactive.OrganizedChestState;

import javax.annotation.Nonnull;

public class ChestFilterPage extends InteractiveCustomUIPage<ChestFilterPage.FilterEventData> {

    public static class FilterEventData {
        public String filterInput;

        public static final BuilderCodec<FilterEventData> CODEC =
                BuilderCodec.builder(FilterEventData.class, FilterEventData::new)
                        .append(
                                new KeyedCodec<>("@FilterInput", Codec.STRING),
                                (FilterEventData obj, String val) -> obj.filterInput = val,
                                (FilterEventData obj) -> obj.filterInput
                        )
                        .add()
                        .build();
    }

    private final OrganizedChestState chestState;

    public ChestFilterPage(@Nonnull PlayerRef playerRef, OrganizedChestState chestState) {
        super(playerRef, CustomPageLifetime.CanDismissOrCloseThroughInteraction, FilterEventData.CODEC);
        this.chestState = chestState;
    }

    @Override
    public void build(
            @Nonnull Ref<EntityStore> ref,
            @Nonnull UICommandBuilder cmd,
            @Nonnull UIEventBuilder evt,
            @Nonnull Store<EntityStore> store
    ) {
        cmd.append("Pages/ChestFilterPage.ui");

        evt.addEventBinding(
                CustomUIEventBindingType.Activating,
                "#DoneButton",
                new EventData().append("@FilterInput", "#FilterInput.Value")
        );
    }

    @Override
    public void handleDataEvent(
            @Nonnull Ref<EntityStore> ref,
            @Nonnull Store<EntityStore> store,
            @Nonnull FilterEventData data
    ) {
        Player player = store.getComponent(ref, Player.getComponentType());

        // Split the input by commas to create the filter array
        String[] filterStrings = data.filterInput != null && !data.filterInput.isEmpty()
                ? data.filterInput.split(",")
                : new String[0];

        // Trim whitespace from each filter
        for (int i = 0; i < filterStrings.length; i++) {
            filterStrings[i] = filterStrings[i].trim();
        }

        // Create the SlotFilter object
        OrganizedChestState.SlotFilter slotFilter = OrganizedChestState.SlotFilter.builder()
                .filterStrings(filterStrings)
                .build();

        // Send confirmation
        playerRef.sendMessage(Message.raw("Created filter with " + filterStrings.length + " entries"));

        // Update chest
        chestState.setupSlots(slotFilter);

        // Close the UI
        player.getPageManager().setPage(ref, store, Page.None);
    }
}