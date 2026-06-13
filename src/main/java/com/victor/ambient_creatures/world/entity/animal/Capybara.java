package com.victor.ambient_creatures.world.entity.animal;

import com.victor.ambient_creatures.world.entity.ModEntities;
import com.victor.ambient_creatures.world.entity.ai.goal.TamableFollowParentGoal;
import com.victor.ambient_creatures.util.ModTags;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class Capybara extends TamableAnimal
{
    private static final EntityDataAccessor<Boolean> IDLE = SynchedEntityData.defineId(Capybara.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> WALKING = SynchedEntityData.defineId(Capybara.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> SITTING = SynchedEntityData.defineId(Capybara.class, EntityDataSerializers.BOOLEAN);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkingAnimationState = new AnimationState();
    public final AnimationState sittingAnimationState = new AnimationState();

    private int idleAnimationTimeout = 0;

    public Capybara(EntityType<? extends TamableAnimal> type, Level level)
    {
        super(type, level);
        this.setTame(false, false);
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return Animal.createAnimalAttributes()
                .add(Attributes.MAX_HEALTH, 15)
                .add(Attributes.MOVEMENT_SPEED, 0.2)
                .add(Attributes.FOLLOW_RANGE, 20)
                .add(Attributes.WATER_MOVEMENT_EFFICIENCY, 5)
                .add(Attributes.TEMPT_RANGE, 20)
                .add(Attributes.STEP_HEIGHT, 1)
        ;
    }

    @Override
    protected void registerGoals()
    {
        super.registerGoals();

        // Goals for AI
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(0, new TamableAnimalPanicGoal(1.5D, DamageTypeTags.PANIC_ENVIRONMENTAL_CAUSES));
        this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.15D));
        this.goalSelector.addGoal(3, new FollowOwnerGoal(this, 1.5F, 10F, 2F));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.25D, Ingredient.of(Items.MELON_SLICE), false));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.25D, (stack) -> stack.is(ModTags.Items.CAPYBARA_FOODS), false));
        this.goalSelector.addGoal(5, new TamableFollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(6, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 4.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NonNull Builder builder)
    {
        super.defineSynchedData(builder);

        builder.define(IDLE, false);
        builder.define(WALKING, false);
        builder.define(SITTING, false);
    }

    public boolean isIdle() { return entityData.get(IDLE); }
    public boolean isWalking() { return entityData.get(WALKING); }
    public boolean isSitting() { return entityData.get(SITTING); }

    public void setIdle(boolean isIdle) { this.entityData.set(IDLE, isIdle); }
    public void setWalking(boolean isWalking) { this.entityData.set(WALKING, isWalking); }
    public void setSitting(boolean isSitting) { this.entityData.set(SITTING, isSitting); }

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
        else if (accessor == SITTING)
        {
            this.sittingAnimationState.animateWhen(this.isInSittingPose(), this.tickCount);
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
                setSitting(false);
                break;

            case 1:
                setIdle(false);
                setWalking(true);
                setSitting(false);
                break;

            case 2:
                setIdle(false);
                setWalking(false);
                setSitting(true);
                break;

            default:
                setIdle(true);
                setWalking(false);
                setSitting(false);
                break;
        }
    }

    private int  getAnimationState()
    {
        if (isIdle())
        {
            return 0;
        }
        else if (isWalking())
        {
            return 1;
        }
        else if (isSitting())
        {
            return 2;
        }
        else
        {
            return 0;
        }
    }

    private void setupAnimationStates()
    {
        boolean shouldWalk = this.getDeltaMovement().horizontalDistanceSqr() > 0.001 && !isInSittingPose();
        boolean shouldSit = this.isTame() && this.isInSittingPose() && !this.isInWater() &&!this.isSwimming();
        boolean shouldIdle = idleAnimationTimeout <= 0 && this.getDeltaMovement().horizontalDistanceSqr() <= 0.001 && (!shouldWalk || !shouldSit);

        if (shouldIdle)
        {
            idleAnimationTimeout = 200;

            if (!this.idleAnimationState.isStarted())
            {
                this.idleAnimationState.start(this.age);
                this.setIdle(true);
            }

            this.setSitting(false);
            this.sittingAnimationState.stop();

            this.setWalking(false);
            this.walkingAnimationState.stop();

        }
        else
        {
            --this.idleAnimationTimeout;
        }

        if (shouldSit)
        {
            if (!this.sittingAnimationState.isStarted())
            {
                this.sittingAnimationState.start(this.age);
                this.setSitting(true);
            }

            this.setIdle(false);
            this.idleAnimationState.stop();

            this.setWalking(false);
            this.walkingAnimationState.stop();
        }
        else
        {
            this.sittingAnimationState.stop();
            this.setSitting(false);
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

            this.setSitting(false);
            this.sittingAnimationState.stop();
        }
    }

    @Override
    protected void applyTamingSideEffects()
    {
        // Apply health buff when Capybara becomes tamed
        if (this.isTame())
        {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(40.0F);
            this.setHealth(40.0F);
        }
        else
        {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(15.0F);
        }
    }

    @Override
    public InteractionResult mobInteract(final Player player, final InteractionHand hand)
    {
        ItemStack itemStack = player.getItemInHand(hand);

        if (this.isTame())
        {
            // Try healing if player is holding food and Capybara has health missing
            if (this.isFood(itemStack) && this.getHealth() <= this.getMaxHealth())
            {
                this.feed(player, hand, itemStack, 2.0F, 2.0F);
                return InteractionResult.SUCCESS;
            }
            else
            {
                // Flip sitting state if player owns this Capybara
                InteractionResult result = super.mobInteract(player, hand);

                if (!result.consumesAction() && this.isOwnedBy(player) && !this.isSwimming())
                {
                    this.setOrderedToSit(!this.isOrderedToSit());
                    this.jumping = false;
                    this.navigation.stop();
                    this.setTarget(null);
                    return InteractionResult.SUCCESS.withoutItem();
                }
                else
                {
                    return result;
                }
            }
        }
        else if (!this.level().isClientSide() && itemStack.is(Items.MELON_SLICE))
        {
            // If not tamed, try taming and use one item from player
            itemStack.consume(1, player);
            this.tryToTame(player);
            return InteractionResult.SUCCESS_SERVER;
        }
        else
        {
            // Otherwise, just do the normal interaction
            return super.mobInteract(player, hand);
        }
    }

    private void tryToTame(Player player)
    {
        // Randomize chance for taming item to successfully tame the Capybara
        if (this.random.nextInt(3) == 0)
        {
            // Success
            this.tame(player);
            this.navigation.stop();
            this.setTarget(null);
            this.setOrderedToSit(true);
            this.setSitting(true);
            this.level().broadcastEntityEvent(this, (byte)7);
        }
        else
        {
            // Fail, play fail particles
            this.level().broadcastEntityEvent(this, (byte)6);
        }
    }

    @Override
    public void tick()
    {
        super.tick();

        if (this.level().isClientSide())
        {
            setupAnimationStates();
        }

        // If the Capybara enters water while sitting, allow it to move
        if ((this.isSwimming() || this.isInWater()) && this.isOrderedToSit())
        {
            this.setOrderedToSit(false);
        }
    }

    @Override
    public boolean isFood(ItemStack itemStack) { return itemStack.is(ModTags.Items.CAPYBARA_FOODS); }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob partner)
    {
        return ModEntities.CAPYBARA.create(level, EntitySpawnReason.BREEDING);
    }
}
