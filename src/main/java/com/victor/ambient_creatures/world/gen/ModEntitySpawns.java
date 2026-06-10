package com.victor.ambient_creatures.world.gen;

import com.victor.ambient_creatures.entity.ModEntities;
import com.victor.ambient_creatures.entity.custom.Penguin;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;

public class ModEntitySpawns
{
    public static boolean canPenguinSpawn(EntityType<Penguin> type, ServerLevelAccessor level, EntitySpawnReason spawnReason, BlockPos blockPos, RandomSource random)
    {
        BlockState stateBelow = level.getBlockState(blockPos.below());

        if (Animal.checkAnimalSpawnRules(type, level, spawnReason, blockPos, random))
        {
            return true;
        }

        if (stateBelow.is(Blocks.ICE) || stateBelow.is(Blocks.PACKED_ICE) || stateBelow.is(Blocks.BLUE_ICE))
        {
            return true;
        }

        if (level.getBlockState(blockPos).getBlock() == Blocks.WATER && level.getBlockState(blockPos.above()).isAir())
        {
            return true;
        }

        return false;
    }

//    public static boolean canCapybaraSpawn(EntityType<Capybara> type, ServerLevelAccessor level, EntitySpawnReason spawnReason, BlockPos blockPos, RandomSource random)
//    {
//
//        if (Animal.checkAnimalSpawnRules(type, level,spawnReason, blockPos, random))
//        {
//            return true;
//        }
//
//        if (level.getBlockState(blockPos).getBlock() == Blocks.WATER && level.getBlockState(blockPos.above()).isAir())
//        {
//            return true;
//        }
//
//        return false;
//    }

    public static void  addSpawns()
    {
//        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.SWAMP, Biomes.MANGROVE_SWAMP, Biomes.JUNGLE,
//                        Biomes.SPARSE_JUNGLE, Biomes.BAMBOO_JUNGLE, Biomes.RIVER, Biomes.SAVANNA),
//                MobCategory.CREATURE, ModEntities.CAPYBARA, 30, 3, 6);
//
//        SpawnPlacements.register(ModEntities.CAPYBARA, SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModEntitySpawns::canCapybaraSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.STONY_SHORE, Biomes.ICE_SPIKES, Biomes.FROZEN_OCEAN, Biomes.DEEP_FROZEN_OCEAN, Biomes.FROZEN_RIVER),
                MobCategory.CREATURE, ModEntities.PENGUIN, 50, 3, 10);

        SpawnPlacements.register(ModEntities.PENGUIN, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModEntitySpawns::canPenguinSpawn);
    }
}
