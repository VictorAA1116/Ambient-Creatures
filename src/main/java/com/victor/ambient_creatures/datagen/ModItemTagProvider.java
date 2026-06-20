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
        builder(ModTags.Items.CAPYBARA_FOODS)
                .add(Items.SHORT_GRASS.builtInRegistryHolder().key())
                .add(Items.KELP.builtInRegistryHolder().key())
                .add(Items.SEAGRASS.builtInRegistryHolder().key())
        ;

        builder(ModTags.Items.PENGUIN_FOODS)
                .add(Items.COD.builtInRegistryHolder().key())
                .add(Items.SALMON.builtInRegistryHolder().key())
                .add(Items.TROPICAL_FISH.builtInRegistryHolder().key())
        ;

        builder(ModTags.Items.RACCOON_FOODS)
                .add(Items.APPLE.builtInRegistryHolder().key())
                .add(Items.BREAD.builtInRegistryHolder().key())
                .add(Items.CARROT.builtInRegistryHolder().key())
                .add(Items.POTATO.builtInRegistryHolder().key())
                .add(Items.SWEET_BERRIES.builtInRegistryHolder().key())
                .add(Items.EGG.builtInRegistryHolder().key())
                .add(Items.COD.builtInRegistryHolder().key())
                .add(Items.COOKED_COD.builtInRegistryHolder().key())
                .add(Items.SALMON.builtInRegistryHolder().key())
                .add(Items.COOKED_SALMON.builtInRegistryHolder().key())
                .add(Items.CHICKEN.builtInRegistryHolder().key())
                .add(Items.COOKED_CHICKEN.builtInRegistryHolder().key())
        ;
    }
}
