package com.victor.ambient_creatures.world.entity.animal;

import com.victor.ambient_creatures.util.ModTags;
import com.victor.ambient_creatures.world.entity.ModEntities;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;

public class Raccoon extends Animal
{
    private static final EntityDataAccessor<Boolean> IDLE = SynchedEntityData.defineId(Raccoon.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> WALKING = SynchedEntityData.defineId(Raccoon.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> STANDING = SynchedEntityData.defineId(Raccoon.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> SLEEPING = SynchedEntityData.defineId(Raccoon.class, EntityDataSerializers.BOOLEAN);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkingAnimationState = new AnimationState();
    public final AnimationState sleepingAnimationState = new AnimationState();
    public final AnimationState standingAnimationState = new AnimationState();

    private int idleAnimationTimeout = 0;

    public Raccoon(EntityType<? extends Animal> type, Level level)
    {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.WATER_MOVEMENT_EFFICIENCY, 1.5D)
                .add(Attributes.STEP_HEIGHT, 1.0D)
                .add(Attributes.TEMPT_RANGE, 15)
        ;
    }

    @Override
    protected void registerGoals()
    {
        super.registerGoals();

        this.goalSelector.addGoal(0, new BreathAirGoal(this));
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.5));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.15));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1, (stack) -> stack.is(ModTags.Items.RACCOON_FOODS), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(6, new RandomStrollGoal(this, 1));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 4));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder)
    {
        super.defineSynchedData(builder);

        builder.define(IDLE, false);
        builder.define(WALKING, false);
        builder.define(STANDING, false);
        builder.define(SLEEPING, false);
    }

    public boolean isIdle() { return this.entityData.get(IDLE); }
    public boolean isWalking() { return this.entityData.get(WALKING); }
    public boolean isSleeping() { return this.entityData.get(SLEEPING); }
    public boolean isStanding() { return this.entityData.get(STANDING); }

    public void setIdle(boolean idle) { this.entityData.set(IDLE, idle); }
    public void setWalking(boolean walking) { this.entityData.set(WALKING, walking); }
    public void setSleeping(boolean sleeping) { this.entityData.set(SLEEPING, sleeping); }
    public void setStanding(boolean standing) { this.entityData.set(STANDING, standing); }

    @Override
    public void onSyncedDataUpdated(final EntityDataAccessor<?> accessor)
    {
        super.onSyncedDataUpdated(accessor);

        if (accessor == IDLE)
        {
            this.idleAnimationState.animateWhen(this.isIdle(), this.tickCount);
        }
        else if (accessor == WALKING)
        {
            this.walkingAnimationState.animateWhen(this.isWalking(), this.tickCount);
        }
        else if (accessor == STANDING)
        {
            this.standingAnimationState.animateWhen(this.isStanding(), this.tickCount);
        }
        else if (accessor == SLEEPING)
        {
            this.sleepingAnimationState.animateWhen(this.isSleeping(), this.tickCount);
        }
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput valueOutput)
    {
        super.addAdditionalSaveData(valueOutput);

        valueOutput.putInt("AnimationState", getAnimationState());
    }

    @Override
    protected void readAdditionalSaveData(ValueInput valueInput)
    {
        super.readAdditionalSaveData(valueInput);

        int animationState = valueInput.getInt("AnimationState").orElse(0);

        switch (animationState)
        {
            case 0:
                setIdle(true);
                setWalking(false);
                setSleeping(false);
                setStanding(false);
                break;

            case 1:
                setIdle(false);
                setWalking(true);
                setSleeping(false);
                setStanding(false);
                break;

            case 2:
                setIdle(false);
                setWalking(false);
                setSleeping(true);
                setStanding(false);
                break;

            case 3:
                setIdle(false);
                setWalking(false);
                setSleeping(false);
                setStanding(true);
                break;

            default:
                setIdle(false);
                setWalking(false);
                setSleeping(false);
                setStanding(false);
                break;
        }
    }

    private int getAnimationState()
    {
        if (isIdle())
        {
            return 0;
        }
        else if (isWalking())
        {
            return 1;
        }
        else if (isSleeping())
        {
            return 2;
        }
        else if (isStanding())
        {
            return 3;
        }
        else
        {
            return 0;
        }
    }

    private void setupAnimationStates()
    {
        boolean shouldWalk = this.getDeltaMovement().horizontalDistanceSqr() > 0.001;
        boolean shouldIdle = idleAnimationTimeout <= 0 && this.getDeltaMovement().horizontalDistanceSqr() <= 0.001;
        boolean shouldStand = !shouldWalk && this.random.nextFloat() < 0.005F;

        if (shouldIdle)
        {
            idleAnimationTimeout = 200;

            if (!this.idleAnimationState.isStarted())
            {
                this.idleAnimationState.start(this.age);
                this.setIdle(true);
            }

            this.setSleeping(false);
            this.sleepingAnimationState.stop();

        }
        else
        {
            --this.idleAnimationTimeout;
        }

        if (shouldStand)
        {
            if (!standingAnimationState.isStarted())
            {
                this.standingAnimationState.start(this.age);
                this.setStanding(true);
            }

            this.setWalking(false);
            this.walkingAnimationState.stop();

            this.setSleeping(false);
            this.sleepingAnimationState.stop();
        }

        if (shouldWalk)
        {
            if (!walkingAnimationState.isStarted())
            {
                this.walkingAnimationState.start(this.age);
                this.setWalking(true);
            }

            this.setStanding(false);
            this.standingAnimationState.stop();

            this.setSleeping(false);
            this.sleepingAnimationState.stop();
        }
    }

    @Override
    public void tick()
    {
        super.tick();

        if (this.level().isClientSide())
        {
            this.setupAnimationStates();
        }
    }

    @Override
    public void aiStep()
    {
        super.aiStep();
    }



    @Override
    public boolean isFood(ItemStack itemStack)
    {
        return false;
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob partner)
    {
        return ModEntities.RACCOON.create(level, EntitySpawnReason.BREEDING);
    }
}
