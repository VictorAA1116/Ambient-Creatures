package com.victor.ambient_creatures.datagen;

import com.victor.ambient_creatures.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagsProvider.ItemTagsProvider
{
    public ModItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, registryLookupFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries)
    {
        builder(ModTags.Items.CAPYBARA_FOODS)
                .add(BuiltInRegistries.ITEM.getResourceKey(Items.SHORT_GRASS).orElseThrow())
                .add(BuiltInRegistries.ITEM.getResourceKey(Items.KELP).orElseThrow())
                .add(BuiltInRegistries.ITEM.getResourceKey(Items.SEAGRASS).orElseThrow())
        ;

        builder(ModTags.Items.OWL_FOODS)
                .add(BuiltInRegistries.ITEM.getResourceKey(Items.RABBIT).orElseThrow())
        ;

        builder(ModTags.Items.PENGUIN_FOODS)
                .add(BuiltInRegistries.ITEM.getResourceKey(Items.COD).orElseThrow())
                .add(BuiltInRegistries.ITEM.getResourceKey(Items.SALMON).orElseThrow())
                .add(BuiltInRegistries.ITEM.getResourceKey(Items.TROPICAL_FISH).orElseThrow())
        ;

        builder(ModTags.Items.RACCOON_FOODS)
                .add(BuiltInRegistries.ITEM.getResourceKey(Items.APPLE).orElseThrow())
                .add(BuiltInRegistries.ITEM.getResourceKey(Items.BREAD).orElseThrow())
                .add(BuiltInRegistries.ITEM.getResourceKey(Items.CARROT).orElseThrow())
                .add(BuiltInRegistries.ITEM.getResourceKey(Items.POTATO).orElseThrow())
                .add(BuiltInRegistries.ITEM.getResourceKey(Items.SWEET_BERRIES).orElseThrow())
                .add(BuiltInRegistries.ITEM.getResourceKey(Items.EGG).orElseThrow())
                .add(BuiltInRegistries.ITEM.getResourceKey(Items.COD).orElseThrow())
                .add(BuiltInRegistries.ITEM.getResourceKey(Items.COOKED_COD).orElseThrow())
                .add(BuiltInRegistries.ITEM.getResourceKey(Items.SALMON).orElseThrow())
                .add(BuiltInRegistries.ITEM.getResourceKey(Items.COOKED_SALMON).orElseThrow())
                .add(BuiltInRegistries.ITEM.getResourceKey(Items.CHICKEN).orElseThrow())
                .add(BuiltInRegistries.ITEM.getResourceKey(Items.COOKED_CHICKEN).orElseThrow())
        ;
    }
}
