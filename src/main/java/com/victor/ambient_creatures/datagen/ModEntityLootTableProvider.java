package com.victor.ambient_creatures.datagen;

import com.victor.ambient_creatures.world.entity.ModEntities;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricEntityLootSubProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.providers.number.floats.ContextFloatProviders;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

import java.util.concurrent.CompletableFuture;

public class ModEntityLootTableProvider extends FabricEntityLootSubProvider
{
    public ModEntityLootTableProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture)
    {
        super(output, registriesFuture);
    }

    @Override
    public void generate()
    {
        this.add(ModEntities.CAPYBARA,
                LootTable.lootTable()
                        .pool(LootPool.lootPool()
                                .setRolls(ContextIntProviders.exactly(1))
                                .add(LootItem.lootTableItem(Items.SEAGRASS))
                                .add(LootItem.lootTableItem(Items.KELP))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                                .build()
                        )
        );

        this.add(ModEntities.OWL,
                LootTable.lootTable()
                        .pool(LootPool.lootPool()
                                .setRolls(ContextIntProviders.exactly(1))
                                .add(LootItem.lootTableItem(Items.FEATHER).setWeight(2)
                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 2)))
                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0, 2))))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                                .build()
                        )
        );

        this.add(ModEntities.PENGUIN,
                LootTable.lootTable()
                        .pool(LootPool.lootPool()
                                .setRolls(ContextIntProviders.exactly(1))
                                .add(LootItem.lootTableItem(Items.BONE_MEAL))
                                .add(LootItem.lootTableItem(Items.FEATHER).setWeight(2)
                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 2)))
                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0, 2))))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                                .build()
                        )
        );

        this.add(ModEntities.RACCOON,
                LootTable.lootTable()
                        .pool(LootPool.lootPool()
                                .setRolls(ContextIntProviders.exactly(1))
                                .add(LootItem.lootTableItem(Items.RABBIT_FOOT).setWeight(2)
                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 2)))
                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0, 2))))
                                .add(LootItem.lootTableItem(Items.RABBIT_HIDE))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                                .build()
                        )
        );
    }
}
