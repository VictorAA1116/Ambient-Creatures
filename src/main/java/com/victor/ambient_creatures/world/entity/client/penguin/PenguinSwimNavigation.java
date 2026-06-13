package com.victor.ambient_creatures.world.entity.client.penguin;

import com.victor.ambient_creatures.world.entity.animal.Penguin;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class PenguinSwimNavigation extends AmphibiousPathNavigation
{
    public PenguinSwimNavigation(Penguin owner, Level level) {
        super(owner, level);
    }

    @Override
    public boolean isStableDestination(BlockPos pos) {
        if (this.mob instanceof Penguin penguin) {
            if (penguin.travelPos != null) {
                return this.level.getBlockState(pos).is(Blocks.WATER);
            }
        }

        return !this.level.getBlockState(pos.below()).isAir();
    }
}
