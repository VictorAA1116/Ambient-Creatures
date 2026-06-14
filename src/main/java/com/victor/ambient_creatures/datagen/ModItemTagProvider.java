package com.victor.ambient_creatures.datagen;

import com.victor.ambient_creatures.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
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
        valueLookupBuilder(ModTags.Items.CAPYBARA_FOODS)
                .add(Items.SHORT_GRASS)
                .add(Items.KELP)
                .add(Items.SEAGRASS)
        ;

        valueLookupBuilder(ModTags.Items.PENGUIN_FOODS)
                .add(Items.COD)
                .add(Items.SALMON)
                .add(Items.TROPICAL_FISH)
        ;

        valueLookupBuilder(ModTags.Items.RACCOON_FOODS)
                .add(Items.APPLE)
                .add(Items.BREAD)
                .add(Items.CARROT)
                .add(Items.POTATO)
        ;
    }
}
