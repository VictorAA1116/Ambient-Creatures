package com.victor.ambient_creatures.world.entity.animal;

import com.victor.ambient_creatures.sound.ModSounds;
import com.victor.ambient_creatures.util.ModTags;
import com.victor.ambient_creatures.world.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

public class Owl extends Animal
{
    private Vec3 moveTargetPoint;

    private static final EntityDataAccessor<Boolean> IDLE = SynchedEntityData.defineId(Owl.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> WALKING = SynchedEntityData.defineId(Owl.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> FLYING = SynchedEntityData.defineId(Owl.class, EntityDataSerializers.BOOLEAN);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkingAnimationState = new AnimationState();
    public final AnimationState flyingAnimationState = new AnimationState();

    public Owl(EntityType<? extends Animal> type, Level level)
    {
        super(type, level);
        this.moveTargetPoint = Vec3.ZERO;

        this.moveControl = new FlyingMoveControl(this, 10, false);
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return Animal.createAnimalAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.FLYING_SPEED, 10.0D)
                .add(Attributes.STEP_HEIGHT, 1)
        ;
    }

    protected @NotNull PathNavigation createNavigation(final Level level)
    {
        FlyingPathNavigation flyingPathNavigation = new FlyingPathNavigation(this, level);
        flyingPathNavigation.setCanOpenDoors(false);
        flyingPathNavigation.setCanFloat(true);

        return flyingPathNavigation;
    }

    @Override
    protected void registerGoals()
    {
        super.registerGoals();

        // Goals for AI
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.5));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.15));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1, (stack) -> stack.is(ModTags.Items.OWL_FOODS), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(5, new OwlWanderGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder)
    {
        super.defineSynchedData(builder);
        builder.define(IDLE, false);
        builder.define(WALKING, false);
        builder.define(FLYING, false);
    }

    public boolean isIdle() { return this.entityData.get(IDLE); }
    public boolean isWalking() { return this.entityData.get(WALKING); }
    public boolean isFlying() { return this.entityData.get(FLYING); }

    public void setIdle(boolean idle) { this.entityData.set(IDLE, idle); }
    public void setWalking(boolean walking) { this.entityData.set(WALKING, walking); }
    public void setFlying(boolean flying) { this.entityData.set(FLYING, flying); }

    @Override
    public void tick()
    {
        super.tick();

        if (this.level().isClientSide())
        {
            this.setupAnimationStates();
        }
    }

    private void setupAnimationStates()
    {
        boolean shouldWalk = this.getDeltaMovement().horizontalDistanceSqr() > 0.001;
        boolean shouldIdle = this.getDeltaMovement().horizontalDistanceSqr() <= 0.001;
        boolean shouldFly = !this.onGround();

        if (shouldIdle)
        {
            if (!this.idleAnimationState.isStarted())
            {
                this.idleAnimationState.start(this.age);
                this.setIdle(true);
            }

            this.setWalking(false);
            this.walkingAnimationState.stop();

            this.setFlying(false);
            this.flyingAnimationState.stop();

        }

        if (shouldWalk)
        {
            if (!walkingAnimationState.isStarted())
            {
                this.walkingAnimationState.start(this.age);
                this.setWalking(true);
            }

            this.setIdle(false);
            this.idleAnimationState.stop();

            this.setFlying(false);
            this.flyingAnimationState.stop();
        }

        if (shouldFly)
        {
            if (!flyingAnimationState.isStarted())
            {
                this.flyingAnimationState.start(this.age);
                this.setFlying(true);
            }

            this.setIdle(false);
            this.idleAnimationState.stop();

            this.setWalking(false);
            this.walkingAnimationState.stop();
        }
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound()
    {
        int randomSound = this.random.nextInt(2);

        return switch (randomSound)
        {
            case 0 -> ModSounds.OWL_AMBIENT_1;
            case 1 -> ModSounds.OWL_AMBIENT_2;
            default -> null;
        };
    }

    @Override
    public boolean isFood(ItemStack itemStack) { return itemStack.is(ModTags.Items.OWL_FOODS); }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob partner)
    {
        return ModEntities.OWL.create(level, EntitySpawnReason.BREEDING);
    }

    protected void checkFallDamage(final double ya, final boolean onGround, final BlockState onState, final BlockPos pos) {}

    protected boolean omnidirectionalAirMover() {
        return true;
    }

    private static class OwlWanderGoal extends WaterAvoidingRandomFlyingGoal
    {
        public OwlWanderGoal(final PathfinderMob mob, final double speedModifier)
        {
            super(mob, speedModifier);
        }

        protected @Nullable Vec3 getPosition()
        {
            Vec3 pos = null;

            if (this.mob.isInWater())
            {
                pos = LandRandomPos.getPos(this.mob, 15, 15);
            }

            if (this.mob.getRandom().nextFloat() >= this.probability)
            {
                pos = this.getTreePos();
            }

            return pos == null ? super.getPosition() : pos;
        }

        private @Nullable Vec3 getTreePos()
        {
            BlockPos mobPos = this.mob.blockPosition();
            BlockPos.MutableBlockPos abovePos = new BlockPos.MutableBlockPos();
            BlockPos.MutableBlockPos belowPos = new BlockPos.MutableBlockPos();

            for (BlockPos pos : BlockPos.betweenClosed(Mth.floor(this.mob.getX() - (double)3.0F), Mth.floor(this.mob.getY() - (double)6.0F), Mth.floor(this.mob.getZ() - (double)3.0F), Mth.floor(this.mob.getX() + (double)3.0F), Mth.floor(this.mob.getY() + (double)6.0F), Mth.floor(this.mob.getZ() + (double)3.0F)))
            {
                if (!mobPos.equals(pos))
                {
                    BlockState state = this.mob.level().getBlockState(belowPos.setWithOffset(pos, Direction.DOWN));
                    boolean canSitOn = state.getBlock() instanceof LeavesBlock || state.is(BlockTags.LOGS);

                    if (canSitOn && this.mob.level().isEmptyBlock(pos) && this.mob.level().isEmptyBlock(abovePos.setWithOffset(pos, Direction.UP)))
                    {
                        return Vec3.atBottomCenterOf(pos);
                    }
                }
            }

            return null;
        }
    }
}
