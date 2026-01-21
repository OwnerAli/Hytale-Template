package me.alii.interactive;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.server.core.inventory.container.SimpleItemContainer;
import com.hypixel.hytale.server.core.inventory.container.filter.FilterActionType;
import com.hypixel.hytale.server.core.universe.world.meta.BlockState;
import com.hypixel.hytale.server.core.universe.world.meta.state.ItemContainerState;
import com.hypixel.hytale.server.core.universe.world.worldmap.WorldMapManager;
import lombok.Builder;
import lombok.Getter;
import me.alii.filters.IdContainsFilter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("removal")
@Getter
public class OrganizedChestState extends ItemContainerState {
    public static final Codec<OrganizedChestState> CODEC = BuilderCodec.builder(OrganizedChestState.class, OrganizedChestState::new, BlockState.BASE_CODEC)
            .append(new KeyedCodec<>("Custom", Codec.BOOLEAN),
                    (state, o) -> state.custom = o,
                    state -> state.custom)
            .add()
            .append(new KeyedCodec<>("AllowViewing", Codec.BOOLEAN),
                    (state, o) -> state.allowViewing = o,
                    state -> state.allowViewing)
            .add()
            .append(new KeyedCodec<>("Droplist", Codec.STRING),
                    (state, o) -> state.droplist = o,
                    state -> state.droplist)
            .add()
            .append(new KeyedCodec<>("Marker", WorldMapManager.MarkerReference.CODEC),
                    (state, o) -> state.marker = o,
                    state -> state.marker)
            .add()
            .append(new KeyedCodec<>("ItemContainer", SimpleItemContainer.CODEC),
                    (state, o) -> state.itemContainer = o,
                    state -> state.itemContainer)
            .add()
            .build();

    public void setupSlots(SlotFilter slotFilter) {
        for (int i = 0; i < getItemContainer().getCapacity(); i++) {
            int row = i / 9;

            String filterForRow = determineFilterForRow(slotFilter.filterStrings, row);

            if (filterForRow != null) {
                this.getItemContainer().setSlotFilter(
                        FilterActionType.ADD,
                        (short) i,
                        new IdContainsFilter(filterForRow)
                );
            }
        }
    }

    private String determineFilterForRow(String[] filterStrings, int currentRow) {
        // Try to find a filter with explicit row range [x-y]
        Pattern rangePattern = Pattern.compile("^([^\\[]+)\\[([^]]+)]$");

        for (String filterString : filterStrings) {
            Matcher matcher = rangePattern.matcher(filterString);

            if (matcher.find()) {
                // Format: "ore[0-2]"
                String filter = matcher.group(1);
                String range = matcher.group(2);

                if (isRowInRange(currentRow, range)) {
                    return filter;
                }
            }
        }

        // Fallback: use array index as row number
        // Format: {"ore", "soil"} where index 0 = row 0, index 1 = row 1
        if (currentRow < filterStrings.length) {
            String filterString = filterStrings[currentRow];

            // Check if it has range notation
            Matcher matcher = rangePattern.matcher(filterString);
            if (matcher.find()) {
                return matcher.group(1);
            }

            // Plain filter without range
            return filterString;
        }

        return null;
    }

    private boolean isRowInRange(int row, String range) {
        if (range.contains("-")) {
            // Range format: "0-2"
            String[] parts = range.split("-");
            int start = Integer.parseInt(parts[0].trim());
            int end = Integer.parseInt(parts[1].trim());
            return row >= start && row <= end;
        } else {
            // Single row: "0"
            int targetRow = Integer.parseInt(range.trim());
            return row == targetRow;
        }
    }

    @Builder
    public static class SlotFilter {
        private final String[] filterStrings;

        private SlotFilter(String[] filterStrings) {
            this.filterStrings = filterStrings;
        }
    }
}