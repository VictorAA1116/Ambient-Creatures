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
import org.jspecify.annotations.Nullable;

public class Owl extends Animal
{
    private static final EntityDataAccessor<Boolean> IDLE = SynchedEntityData.defineId(Owl.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> WALKING = SynchedEntityData.defineId(Owl.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> FLYING = SynchedEntityData.defineId(Owl.class, EntityDataSerializers.BOOLEAN);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkingAnimationState = new AnimationState();
    public final AnimationState flyingAnimationState = new AnimationState();

    public Owl(EntityType<? extends Animal> type, Level level)
    {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return Animal.createAnimalAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.FLYING_SPEED, 0.5D)
                .add(Attributes.STEP_HEIGHT, 1)
        ;
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

        if (shouldIdle)
        {
            if (!this.idleAnimationState.isStarted())
            {
                this.idleAnimationState.start(this.age);
                this.setIdle(true);
            }

            this.setWalking(false);
            this.walkingAnimationState.stop();

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
        }
    }

    @Override
    public boolean isFood(ItemStack itemStack) { return itemStack.is(ModTags.Items.OWL_FOODS); }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob partner)
    {
        return ModEntities.OWL.create(level, EntitySpawnReason.BREEDING);
    }
}
