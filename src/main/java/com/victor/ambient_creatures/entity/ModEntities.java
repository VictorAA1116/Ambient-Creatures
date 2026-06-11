package com.victor.ambient_creatures.entity;

import com.victor.ambient_creatures.AmbientCreatures;
import com.victor.ambient_creatures.entity.client.capybara.CapybaraRenderer;
import com.victor.ambient_creatures.entity.client.penguin.PenguinRenderer;
import com.victor.ambient_creatures.entity.custom.Capybara;
import com.victor.ambient_creatures.entity.custom.Penguin;
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
    public static final EntityType<Penguin> PENGUIN = register("penguin",
            EntityType.Builder.of(Penguin::new, MobCategory.CREATURE)
                    .sized(0.5f, 1.0f)
    );

    public static final EntityType<Capybara> CAPYBARA = register("capybara",
            EntityType.Builder.of(Capybara::new, MobCategory.CREATURE)
                    .sized(1.0f, 1.0f)
    );

    public static void registerModEntities()
    {
        AmbientCreatures.LOGGER.info("Registering Mod Entities for " + AmbientCreatures.MOD_ID);

        registerModEntityTypes();
        registerAttributes();
        ModEntityModelLayers.registerModelLayers();

        EntityRenderers.register(PENGUIN, PenguinRenderer::new);
        EntityRenderers.register(CAPYBARA, CapybaraRenderer::new);
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
        FabricDefaultAttributeRegistry.register(PENGUIN, Penguin.createAttributes());
        FabricDefaultAttributeRegistry.register(CAPYBARA, Capybara.createAttributes());
    }
}
