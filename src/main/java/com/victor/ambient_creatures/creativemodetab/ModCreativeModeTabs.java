package com.victor.ambient_creatures.creativemodetab;

import com.victor.ambient_creatures.AmbientCreatures;
import com.victor.ambient_creatures.item.ModItems;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ModCreativeModeTabs
{
    public static final CreativeModeTab AMBIENT_CREATURES_TAB = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB,
            Identifier.fromNamespaceAndPath(AmbientCreatures.MOD_ID, "ambient_creatures"),
            FabricCreativeModeTab.builder().icon(() -> new ItemStack(ModItems.PENGUIN_SPAWN_EGG))
                    .title(Component.translatable("creativemodetab.ambient_creatures.ambient_creatures"))
                    .displayItems((parameters, output) -> {

                        output.accept(ModItems.CAPYBARA_SPAWN_EGG);
                        output.accept(ModItems.PENGUIN_SPAWN_EGG);
                        output.accept(ModItems.RACCOON_SPAWN_EGG);

                    }).build());

    public static void modifyTabs()
    {
        // Spawn Eggs Tab
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.SPAWN_EGGS).register(entries -> {

            entries.insertAfter(Items.POLAR_BEAR_SPAWN_EGG, ModItems.PENGUIN_SPAWN_EGG);
            entries.insertAfter(Items.AXOLOTL_SPAWN_EGG, ModItems.CAPYBARA_SPAWN_EGG);
            entries.insertAfter(Items.FOX_SPAWN_EGG, ModItems.RACCOON_SPAWN_EGG);

        });
    }

    public static void registerCreativeModeTabs()
    {
        AmbientCreatures.LOGGER.info("Registering Creative Mode Tabs for " + AmbientCreatures.MOD_ID);

        modifyTabs();
    }
}
