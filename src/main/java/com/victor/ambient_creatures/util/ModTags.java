package com.victor.ambient_creatures.util;

import com.victor.ambient_creatures.AmbientCreatures;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModTags
{
    public static class Blocks
    {
    }

    public static class Items
    {
        public static final TagKey<Item> CAPYBARA_FOODS = createTag("capybara_foods");
        public static final TagKey<Item> PENGUIN_FOODS = createTag("penguin_foods");
        public static final TagKey<Item> RACCOON_FOODS = createTag("raccoon_foods");

        private static TagKey<Item> createTag(String name)
        {
            return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(AmbientCreatures.MOD_ID, name));
        }
    }
}
