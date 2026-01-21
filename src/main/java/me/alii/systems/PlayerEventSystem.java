package me.alii.systems;

import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.EcsEvent;
import com.hypixel.hytale.component.system.EntityEventSystem;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class PlayerEventSystem<T extends EcsEvent> extends EntityEventSystem<EntityStore, T> {

    protected PlayerEventSystem(@NonNullDecl Class<T> eventType) {
        super(eventType);
    }

    @Override
    public void handle(int index, @NonNullDecl ArchetypeChunk<EntityStore> archetypeChunk, @NonNullDecl Store<EntityStore> store,
                       @NonNullDecl CommandBuffer<EntityStore> commandBuffer, @NonNullDecl T t) {
        // Archetype = a unique combination of component TYPES
        // (e.g. Player + Health + Inventory)

        /* ArchetypeChunk = a chunk of ENTITIES that all share ONE archetype
         *
         * Archetype: Player + Health + Inventory
         *
         * Chunk rows:
         * index 0 = entity A (Player, Health, Inventory data)
         * index 1 = entity B (Player, Health, Inventory data)
         */

        // index = the row of the entity inside THIS archetype chunk

        // Get the entity reference for the entity at this chunk row
        Ref<EntityStore> entityStoreRef = archetypeChunk.getReferenceTo(index);

        // Retrieve the Player component data for this entity (guaranteed by the query)
        Player player = store.getComponent(entityStoreRef, Player.getComponentType());
        handle(player, entityStoreRef, archetypeChunk, store, commandBuffer, t);
    }

    @NullableDecl
    @Override
    public Query<EntityStore> getQuery() {
        return PlayerRef.getComponentType();
    }

    public abstract void handle(Player player, Ref<EntityStore> storeRef, @NonNullDecl ArchetypeChunk<EntityStore> archetypeChunk, @NonNullDecl Store<EntityStore> store,
                                @NonNullDecl CommandBuffer<EntityStore> commandBuffer, @NonNullDecl T t);
}
