package com.victor.ambient_creatures.entity.client.penguin;

import com.victor.ambient_creatures.entity.custom.PenguinEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class PenguinSwimNavigation extends AmphibiousPathNavigation
{
    public PenguinSwimNavigation(PenguinEntity owner, Level level) {
        super(owner, level);
    }

    @Override
    public boolean isStableDestination(BlockPos pos) {
        if (this.mob instanceof PenguinEntity penguinEntity) {
            if (penguinEntity.travelPos != null) {
                return this.level.getBlockState(pos).is(Blocks.WATER);
            }
        }

        return !this.level.getBlockState(pos.below()).isAir();
    }
}
