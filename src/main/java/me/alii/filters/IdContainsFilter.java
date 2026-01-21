package me.alii.filters;

import com.hypixel.hytale.server.core.asset.type.item.config.Item;
import com.hypixel.hytale.server.core.inventory.container.filter.ItemSlotFilter;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class IdContainsFilter implements ItemSlotFilter {
    private final String id;

    public IdContainsFilter(String id) {
        this.id = id;
    }

    @Override
    public boolean test(@NullableDecl Item item) {
        return item.getId() != null && item.getId().toLowerCase().contains(id.toLowerCase());
    }
}
