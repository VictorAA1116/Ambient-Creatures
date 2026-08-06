package com.victor.ambient_creatures.world.entity.ai.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;

import java.util.List;

public class SearchChestsForItemsGoal extends Goal
{
    PathfinderMob mob;
    int horizontalRange = 5;
    int verticalRange = 5;

    public SearchChestsForItemsGoal(PathfinderMob mob, int horizontalRange, int verticalRange)
    {
        this.mob = mob;
        this.horizontalRange = horizontalRange;
        this.verticalRange = verticalRange;
    }

    public SearchChestsForItemsGoal(PathfinderMob mob)
    {
        this.mob = mob;
    }

    @Override
    public boolean canUse()
    {
        return false;
    }

    private BlockPos FindClosestChest()
    {
        List<ChunkPos> list = ChunkPos.rangeClosed(ChunkPos.containing(mob.blockPosition()), Math.floorDiv(this.horizontalRange, 16) + 1).toList();
        BlockPos targetPos = null;

        double closestDistance = (double)Float.MAX_VALUE;

        for(ChunkPos chunkPos : list)
        {
            LevelChunk levelChunk = this.mob.level().getChunkSource().getChunkNow(chunkPos.x(), chunkPos.z());

            if (levelChunk != null)
            {
                for(BlockEntity potentialTarget : levelChunk.getBlockEntities().values())
                {
                    if (potentialTarget instanceof ChestBlockEntity)
                    {
                        ChestBlockEntity chestBlockEntity = (ChestBlockEntity)potentialTarget;

                        double distance = chestBlockEntity.getBlockPos().distToCenterSqr(mob.position());

                        if (distance < closestDistance)
                        {
                            targetPos = chestBlockEntity.getBlockPos();
                            closestDistance = distance;
                        }
                    }
                }
            }
        }

        return targetPos;
    }

}
