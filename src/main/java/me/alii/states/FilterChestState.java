package me.alii.states;

import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.server.core.inventory.container.filter.FilterActionType;
import com.hypixel.hytale.server.core.universe.world.meta.BlockState;
import com.hypixel.hytale.server.core.universe.world.meta.state.ItemContainerState;
import me.alii.filters.IdContainsFilter;

@SuppressWarnings("removal")
public class FilterChestState extends ItemContainerState {
    public static final BuilderCodec<FilterChestState> CODEC =
            BuilderCodec.builder(
                    FilterChestState.class,
                    FilterChestState::new,
                    BlockState.BASE_CODEC).build();

    public void setupRowFilters(FilterData filterData) {
        for (short i = 0; i < getItemContainer().getCapacity(); i++) {
            int row = i / 9;

            String[] ids = filterData.ids;

            // "Ore", "Dirt" length == 2
            // 0, 1
            if (!(row < ids.length)) break;

            String id = ids[row];
            this.getItemContainer().setSlotFilter(
                    FilterActionType.ADD,
                    i,
                    new IdContainsFilter(id)
            );
        }
    }

    public static class FilterData {
        private final String[] ids;

        public FilterData(String... ids) {
            this.ids = ids;
        }
    }
}
