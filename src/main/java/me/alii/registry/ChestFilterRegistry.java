package me.alii.registry;

import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChestFilterRegistry {
    private final Map<BlockType, String[]> blockTypeFilterMap;

    public ChestFilterRegistry() {
        this.blockTypeFilterMap = new ConcurrentHashMap<>();
    }

    public void register(BlockType blockType, String... filter) {
        this.blockTypeFilterMap.put(blockType, filter);
    }
}
