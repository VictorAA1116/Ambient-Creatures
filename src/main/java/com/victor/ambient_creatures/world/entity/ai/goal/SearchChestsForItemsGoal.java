package com.victor.ambient_creatures.world.entity.ai.goal;

import com.victor.ambient_creatures.world.entity.animal.Raccoon;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.pathfinder.Path;

import java.util.List;
import java.util.function.Predicate;

public class SearchChestsForItemsGoal extends Goal
{
    private enum SearchChestsState
    {
        IDLE,
        TRAVELLING,
        LOOTING_CHEST,
        DONE,
    }

    private final PathfinderMob mob;
    private final Predicate<ItemStack> items;
    private final int horizontalRange;
    private final int verticalRange;
    private final double speedModifier;

    private SearchChestsState state = SearchChestsState.IDLE;
    private final int cooldownTicks;
    private int cooldownTimer = 0;
    private int pathRecalcTicks = 0;
    private int chestOpenTicks = 0;
    private BlockPos targetChestPos;
    private BlockPos targetChestAccessPos;
    private ChestBlockEntity targetChest;

    public SearchChestsForItemsGoal(PathfinderMob mob, Predicate<ItemStack> items, int horizontalRange, int verticalRange, double speedModifier, int cooldownTicks)
    {
        this.mob = mob;
        this.items = items;
        this.horizontalRange = horizontalRange;
        this.verticalRange = verticalRange;
        this.speedModifier = speedModifier;
        this.cooldownTicks = cooldownTicks;
        this.setFlags(java.util.EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse()
    {
        // Don't search for chests if we already have an item
        if (!this.mob.getMainHandItem().isEmpty())
        {
            return false;
        }

        if (this.cooldownTimer > 0)
        {
            --this.cooldownTimer;
            return false;
        }

        return true;
    }

    @Override
    public boolean canContinueToUse()
    {
        return this.state != SearchChestsState.IDLE && this.state != SearchChestsState.DONE;
    }

    @Override
    public void start()
    {
        this.state = SearchChestsState.TRAVELLING;
        this.pathRecalcTicks = 0;
    }

    @Override
    public void stop()
    {
        // Close the chest properly
        if (this.targetChest != null && this.mob instanceof Raccoon raccoon)
        {
            this.targetChest.stopOpen(raccoon);
            raccoon.clearOpenedChestPos();
        }

        this.targetChest = null;
        this.targetChestPos = null;
        this.targetChestAccessPos = null;
        this.state = SearchChestsState.IDLE;
        this.cooldownTimer = cooldownTicks;
        this.chestOpenTicks = 0;
    }

    @Override
    public void tick()
    {
        switch (this.state)
        {
            case TRAVELLING:
                this.travelToChest();
                break;

            case LOOTING_CHEST:
                this.lootChest();
                break;

            case DONE:
                this.stop();
                break;
        }
    }

    private void travelToChest()
    {
        // Recalculate path every 10 ticks
        if (--this.pathRecalcTicks <= 0)
        {
            this.pathRecalcTicks = 10;
            this.targetChestPos = this.findClosestChest();

            if (this.targetChestPos != null)
            {
                if (this.targetChestAccessPos == null)
                {
                    this.state = SearchChestsState.DONE;
                    return;
                }

                // Move towards the chest
                this.mob.getNavigation().moveTo(
                        this.targetChestAccessPos.getX() + 0.5,
                        this.targetChestAccessPos.getY(),
                        this.targetChestAccessPos.getZ() + 0.5,
                        this.speedModifier
                );
            }
            else
            {
                // No chest found, go idle
                this.state = SearchChestsState.DONE;
            }
        }

        // Check if we've reached the chest
        if (this.targetChestPos != null)
        {
            double distanceSq = this.mob.distanceToSqr(
                    this.targetChestAccessPos.getX() + 0.5,
                    this.targetChestAccessPos.getY() + 0.5,
                    this.targetChestAccessPos.getZ() + 0.5
            );

            if (distanceSq < 2.25 || this.mob.getNavigation().isDone())
            {
                this.mob.getNavigation().stop();
                this.state = SearchChestsState.LOOTING_CHEST;
                if (this.mob instanceof Raccoon raccoon)
                {
                    raccoon.setOpenedChestPos(this.targetChestPos);
                }
            }
        }
    }

    private void lootChest()
    {
        if (this.targetChest == null || this.targetChestPos == null)
        {
            this.state = SearchChestsState.DONE;
            return;
        }

        // Open the chest on first interaction
        if (this.chestOpenTicks == 0 && this.mob instanceof Raccoon raccoon)
        {
            this.targetChest.startOpen(raccoon);
            raccoon.setOpenedChestPos(this.targetChestPos);
        }

        this.chestOpenTicks++;

        // Take item halfway through the chest open duration (5 ticks)
        if (this.chestOpenTicks == 5)
        {
            ItemStack stolenItem = this.takeItemFromChest(this.targetChest);

            if (!stolenItem.isEmpty())
            {
                // Successfully took an item, hold it, and set as a guaranteed drop
                this.mob.setItemSlot(EquipmentSlot.MAINHAND, stolenItem);
                this.mob.setGuaranteedDrop(EquipmentSlot.MAINHAND);
            }
        }

        // Keep chest open for 10 ticks, then finish
        if (this.chestOpenTicks >= 10)
        {
            this.state = SearchChestsState.DONE;
            this.chestOpenTicks = 0;
        }
    }

    private BlockPos findClosestChest()
    {
        List<ChunkPos> chunkList = ChunkPos.rangeClosed(
                ChunkPos.containing(this.mob.blockPosition()),
                Math.floorDiv(this.horizontalRange, 16) + 1
        ).toList();

        BlockPos closestChestPos = null;
        double closestDistance = Double.MAX_VALUE;

        for (ChunkPos chunkPos : chunkList)
        {
            LevelChunk levelChunk = this.mob.level().getChunkSource().getChunkNow(chunkPos.x(), chunkPos.z());

            if (levelChunk == null) continue;

            for (BlockEntity blockEntity : levelChunk.getBlockEntities().values())
            {
                if (blockEntity instanceof ChestBlockEntity chest)
                {
                    BlockPos chestPos = chest.getBlockPos();

                    // Check vertical range
                    int vertDiff = Math.abs(chestPos.getY() - this.mob.blockPosition().getY());
                    if (vertDiff > this.verticalRange) continue;

                    double distance = this.mob.distanceToSqr(
                            chestPos.getX() + 0.5,
                            chestPos.getY() + 0.5,
                            chestPos.getZ() + 0.5
                    );

                    if (distance < closestDistance)
                    {
                        // Check if chest has food items
                        BlockPos accessPos = this.findReachableChestAccessPos(chestPos);
                        if (this.hasMatchingItems(chest) && accessPos != null)
                        {
                            this.targetChest = chest;
                            closestChestPos = chestPos;
                            this.targetChestAccessPos = accessPos;
                            closestDistance = distance;
                        }
                    }
                }
            }
        }

        return closestChestPos;
    }

    private boolean hasMatchingItems(ChestBlockEntity chest)
    {
        for (int i = 0; i < chest.getContainerSize(); i++)
        {
            ItemStack stack = chest.getItem(i);
            if (!stack.isEmpty() && this.items.test(stack))
            {
                return true;
            }
        }
        return false;
    }

    private ItemStack takeItemFromChest(Container container)
    {
        int slot = 0;

        // Look through all slots in chest, if it contains a matching item, take one
        for (ItemStack itemStack : container)
        {
            if (!itemStack.isEmpty() && this.items.test(itemStack))
            {
                ItemStack takenItem = container.removeItem(slot, 1);
                container.setChanged();
                return takenItem;
            }

            slot++;
        }

        return ItemStack.EMPTY;
    }

    private boolean canPathToChest(BlockPos chestPos)
    {
        Path path = this.mob.getNavigation().createPath(chestPos, 0);
        return path != null && path.canReach();
    }

    private BlockPos findReachableChestAccessPos(BlockPos chestPos)
    {
        for (Direction direction : Direction.Plane.HORIZONTAL)
        {
            BlockPos accessPos = chestPos.relative(direction);
            if (this.canPathToChest(accessPos))
            {
                return accessPos;
            }
        }

        return null;
    }
}