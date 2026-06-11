package com.victor.ambient_creatures.item;

import com.victor.ambient_creatures.AmbientCreatures;
import com.victor.ambient_creatures.entity.ModEntities;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

import java.util.function.Function;

public class ModItems
{
    public static final Item PENGUIN_SPAWN_EGG = registerItem("penguin_spawn_egg", SpawnEggItem::new, new Item.Properties().spawnEgg(ModEntities.PENGUIN));
    public static final Item CAPYBARA_SPAWN_EGG = registerItem("capybara_spawn_egg", SpawnEggItem::new, new Item.Properties().spawnEgg(ModEntities.CAPYBARA));

    private static Item registerItem(String name, Function<Item.Properties, Item> function, Item.Properties properties)
    {
        return Registry.register (
                BuiltInRegistries.ITEM,
                Identifier.fromNamespaceAndPath(AmbientCreatures.MOD_ID, name),
                function.apply(properties.setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(AmbientCreatures.MOD_ID, name))))
        );
    }

    public static void registerModItems()
    {
        AmbientCreatures.LOGGER.info("Registering Mod Items for " + AmbientCreatures.MOD_ID);
    }
}
