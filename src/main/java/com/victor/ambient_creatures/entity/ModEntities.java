package com.victor.ambient_creatures.entity;

import com.victor.ambient_creatures.AmbientCreatures;
import com.victor.ambient_creatures.entity.client.penguin.PenguinEntityRenderer;
import com.victor.ambient_creatures.entity.custom.PenguinEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntities
{
    public static final EntityType<PenguinEntity> PENGUIN = register("penguin",
            EntityType.Builder.of(PenguinEntity::new, MobCategory.AMBIENT)
                    .sized(0.5f, 1.0f)
    );

    public static void registerModEntities()
    {
        AmbientCreatures.LOGGER.info("Registering Mod Entities for " + AmbientCreatures.MOD_ID);

        registerModEntityTypes();
        registerAttributes();
        ModEntityModelLayers.registerModelLayers();

        EntityRenderers.register(PENGUIN, PenguinEntityRenderer::new);
    }

    private static <T extends Entity> EntityType<T> register(String name, EntityType.Builder<T> builder)
    {
        ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(AmbientCreatures.MOD_ID, name));

        return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
    }

    public static void registerModEntityTypes()
    {
        AmbientCreatures.LOGGER.info("Registering EntityTypes for " + AmbientCreatures.MOD_ID);
    }

    public static void registerAttributes()
    {
        FabricDefaultAttributeRegistry.register(PENGUIN, PenguinEntity.createAttributes());
    }
}
