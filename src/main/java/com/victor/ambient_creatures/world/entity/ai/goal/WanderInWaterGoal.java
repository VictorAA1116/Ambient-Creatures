package com.victor.ambient_creatures.world.entity.ai.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;

public class WanderInWaterGoal extends MoveToBlockGoal {
    private final PathfinderMob pathfinderMob;

    public WanderInWaterGoal(PathfinderMob pathfinderMob, double speed) {
        super(pathfinderMob, speed, 24);
        this.pathfinderMob = pathfinderMob;
        this.verticalSearchStart= -1;
    }

    @Override
    public boolean canContinueToUse()
    {
        return !this.pathfinderMob.isInWater() && this.tryTicks <= 1200 && this.isValidTarget(this.pathfinderMob.level(), this.blockPos);
    }

    @Override
    public boolean canUse()
    {
        if (this.pathfinderMob.isBaby() && !this.pathfinderMob.isInWater())
        {
            return super.canUse();
        }
        else
        {
            return !this.pathfinderMob.onGround() && !this.pathfinderMob.isInWater() && super.canUse();
        }
    }

    @Override
    public boolean shouldRecalculatePath() {
        return this.tryTicks % 160 == 0;
    }

    protected boolean isValidTarget(LevelReader level, BlockPos pos)
    {
        return level.getBlockState(pos).is(Blocks.WATER);
    }
}
