package com.victor.ambient_creatures.entity.custom;

import com.victor.ambient_creatures.entity.ModEntities;
import com.victor.ambient_creatures.entity.client.ai.goal.WanderInWaterGoal;
import com.victor.ambient_creatures.entity.client.penguin.PenguinMoveControl;
import com.victor.ambient_creatures.entity.client.penguin.PenguinSwimNavigation;
import com.victor.ambient_creatures.sound.ModSounds;
import com.victor.ambient_creatures.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.fish.Cod;
import net.minecraft.world.entity.animal.fish.Pufferfish;
import net.minecraft.world.entity.animal.fish.Salmon;
import net.minecraft.world.entity.animal.fish.TropicalFish;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class Penguin extends Animal
{
    private static final EntityDataAccessor<Boolean> IDLE = SynchedEntityData.defineId(Penguin.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> WALKING = SynchedEntityData.defineId(Penguin.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> SWIM_IDLE = SynchedEntityData.defineId(Penguin.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> SWIMMING = SynchedEntityData.defineId(Penguin.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> SLIDING = SynchedEntityData.defineId(Penguin.class, EntityDataSerializers.BOOLEAN);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkingAnimationState = new AnimationState();
    public final AnimationState swimIdleAnimationState = new AnimationState();
    public final AnimationState swimmingAnimationState = new AnimationState();
    public final AnimationState slidingAnimationState = new AnimationState();

    private int slidingCooldown = 0;
    private int eatingTime = 0;
    public BlockPos travelPos;
    boolean landBound;
    private static final Predicate<ItemEntity> PICKABLE_DROP_FILTER;

    public Penguin(EntityType<? extends Animal> type, Level level)
    {
        super(type, level);
        this.setPathfindingMalus(PathType.WATER, 0.0F);
        this.moveControl = new PenguinMoveControl(this);
        this.setCanPickUpLoot(true);
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 15)
                .add(Attributes.MOVEMENT_SPEED, 0.6)
                .add(Attributes.WATER_MOVEMENT_EFFICIENCY, 5)
                .add(Attributes.ATTACK_DAMAGE, 5.0)
                .add(Attributes.STEP_HEIGHT, 1.0)
                .add(Attributes.TEMPT_RANGE, 15)
        ;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.goalSelector.addGoal(0, new BreathAirGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.5));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Salmon.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Cod.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, TropicalFish.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Pufferfish.class, true));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.15));
        this.goalSelector.addGoal(3, new PenguinPickupItemGoal(PICKABLE_DROP_FILTER, 10.0D, 1.2D));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1, (stack) -> stack.is(ModTags.Items.PENGUIN_FOODS), false));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(6, new RandomStrollGoal(this, 1));
        this.goalSelector.addGoal(7, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(8, new RandomSwimmingGoal(this, 1, 1));
        this.goalSelector.addGoal(8, new WanderInWaterGoal(this, 1.1));
        this.goalSelector.addGoal(10, new MeleeAttackGoal(this, 1.5, true));
        this.goalSelector.addGoal(11, new LookAtPlayerGoal(this, Player.class, 4));
        this.goalSelector.addGoal(12, new RandomLookAroundGoal(this));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder)
    {
        super.defineSynchedData(builder);

        builder.define(IDLE, false);
        builder.define(WALKING, false);
        builder.define(SWIM_IDLE, false);
        builder.define(SWIMMING, false);
        builder.define(SLIDING, false);
    }

    public boolean isIdle() { return entityData.get(IDLE); }
    public boolean isWalking() { return entityData.get(WALKING); }
    public boolean isSwimIdle() { return entityData.get(SWIM_IDLE); }
    public boolean isSwimming() { return entityData.get(SWIMMING); }
    public boolean isSliding() { return entityData.get(SLIDING); }

    public void setIdle(boolean isIdle) { this.entityData.set(IDLE, isIdle); }
    public void setWalking(boolean isWalking) { this.entityData.set(WALKING, isWalking); }
    public void setSwimIdle(boolean isSwimIdle) { this.entityData.set(SWIM_IDLE, isSwimIdle); }
    public void setSwimming(boolean isSwimming) { this.entityData.set(SWIMMING, isSwimming); }
    public void setSliding(boolean isSliding) { this.entityData.set(SLIDING, isSliding); }

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
        else if (accessor == SWIM_IDLE)
        {
            this.swimIdleAnimationState.animateWhen(this.isSwimIdle(), this.tickCount);
        }
        else if (accessor == SWIMMING)
        {
            this.swimmingAnimationState.animateWhen(this.isSwimming(), this.tickCount);
        }
        else if (accessor == SLIDING)
        {
            this.slidingAnimationState.animateWhen(this.isSliding(), this.tickCount);
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
                setSwimIdle(false);
                setSwimming(false);
                setSliding(false);
                break;

            case 1:
                setIdle(false);
                setWalking(true);
                setSwimIdle(false);
                setSwimming(false);
                setSliding(false);
                break;

            case 2:
                setIdle(false);
                setWalking(false);
                setSwimIdle(true);
                setSwimming(false);
                setSliding(false);
                break;

            case 3:
                setIdle(false);
                setWalking(false);
                setSwimIdle(false);
                setSwimming(true);
                setSliding(false);
                break;

            case 4:
                setIdle(false);
                setWalking(false);
                setSwimIdle(false);
                setSwimming(false);
                setSliding(true);
                break;

            default:
                setIdle(true);
                setWalking(false);
                setSwimIdle(false);
                setSwimming(false);
                setSliding(false);
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
        else if (isSwimming())
        {
            return 2;
        }
        else if (isSwimIdle())
        {
            return 3;
        }
        else if (isSliding())
        {
            return 4;
        }
        else
        {
            return 0;
        }
    }

    private void setupAnimationStates() {

        if (this.onGround() && !this.isInWater())
        {
            BlockPos below = this.blockPosition().below();
            BlockState blockState = this.level().getBlockState(below);

            boolean isOnIce = blockState.is(Blocks.ICE) || blockState.is(Blocks.PACKED_ICE) || blockState.is(Blocks.BLUE_ICE);

            if (isOnIce)
            {
                if (slidingCooldown <= 0)
                {
                    this.setSliding(this.random.nextFloat() < 0.3F);
                    this.slidingCooldown = 20 + this.random.nextInt(40);
                }
                else
                {
                    slidingCooldown--;
                }
            }
            else
            {
                this.setSliding(false);
                this.slidingCooldown = 0;
            }
        }
        else
        {
            this.setSliding(false);
            this.slidingCooldown = 0;
        }

        boolean isSwimming = this.isInWater() && this.getDeltaMovement().horizontalDistanceSqr() > 0.001;
        boolean isSwimmingIdle = this.isInWater() && this.getDeltaMovement().horizontalDistanceSqr() < 0.001;
        boolean isWalking = !isSliding() && this.onGround() && this.getDeltaMovement().horizontalDistanceSqr() > 0.001;
        boolean shouldIdle = !isSliding() && this.onGround() && this.getDeltaMovement().horizontalDistanceSqr() < 0.001;

        if (isSwimming)
        {
            if (!this.swimmingAnimationState.isStarted())
            {
                this.swimmingAnimationState.start(this.age);
                this.setSwimming(true);
            }

            this.idleAnimationState.stop();
            this.walkingAnimationState.stop();
            this.swimIdleAnimationState.stop();
            this.slidingAnimationState.stop();
        }

        if (isSwimmingIdle)
        {
            if (!this.swimIdleAnimationState.isStarted())
            {
                this.swimIdleAnimationState.start(this.age);
                this.setSwimIdle(true);
            }

            this.idleAnimationState.stop();
            this.walkingAnimationState.stop();
            this.swimmingAnimationState.stop();
            this.slidingAnimationState.stop();
        }

        if (this.isSliding())
        {
            if (!this.slidingAnimationState.isStarted())
            {
                this.slidingAnimationState.start(this.age);
                this.setSliding(true);
            }

            this.idleAnimationState.stop();
            this.walkingAnimationState.stop();
            this.swimmingAnimationState.stop();
            this.swimIdleAnimationState.stop();
        }

        if (isWalking)
        {
            if (!this.walkingAnimationState.isStarted())
            {
                this.walkingAnimationState.start(this.age);
                this.setWalking(true);
            }

            this.idleAnimationState.stop();
            this.swimmingAnimationState.stop();
            this.swimIdleAnimationState.stop();
            this.slidingAnimationState.stop();
        }

        if (shouldIdle)
        {
            if (!this.idleAnimationState.isStarted())
            {
                this.idleAnimationState.start(this.age);
                this.setIdle(true);
            }

            this.walkingAnimationState.stop();
            this.swimmingAnimationState.stop();
            this.swimIdleAnimationState.stop();
            this.slidingAnimationState.stop();
        }
    }


    @Override
    public void tick()
    {
        super.tick();

        // Set Up Animation States
        if (this.level().isClientSide())
        {
            this.setupAnimationStates();
        }

        // Update pitch rotation if swimming vs swim idle, prevent drowning
        if (this.isInWater())
        {
            Vec3 velocity = this.getDeltaMovement();

            if (velocity.lengthSqr() < 0.01)
            {
                this.setXRot(Mth.lerp(0.1F, this.getXRot(), 0.0F));
            }

            this.setAirSupply(this.getMaxAirSupply());
        }

    }

    @Override
    public void aiStep()
    {
        this.tryEat();
        super.aiStep();
    }

    public void tryEat()
    {
        if (this.level().isClientSide() || !this.isAlive() || !this.isEffectiveAi()) return;

        ++this.eatingTime;

        ItemStack itemStack = this.getItemBySlot(EquipmentSlot.MAINHAND);

        if (this.canEat(itemStack))
        {
            if (this.eatingTime > 600)
            {
                ItemStack itemStack2 = itemStack.finishUsingItem(this.level(), this);

                if (!itemStack2.isEmpty())
                {
                    this.setItemSlot(EquipmentSlot.MAINHAND, itemStack2);
                }

                this.eatingTime = 0;
            }
            else if (this.eatingTime > 560 && this.random.nextFloat() < 0.1F)
            {
                this.playEatingSound();
                this.level().broadcastEntityEvent(this, (byte)45);
            }
        }
    }

    private boolean canEat(ItemStack itemStack)
    {
        return itemStack.has(DataComponents.FOOD) && itemStack.has(DataComponents.CONSUMABLE) && this.getTarget() == null;
    }

    @Override
    protected void playEatingSound()
    {
        this.playSound(SoundEvents.FOX_EAT, 1.0F, 1.0F);
    }

    @Override
    public void handleEntityEvent(byte status)
    {
        if (status == 45)
        {
            ItemStack itemStack = this.getItemBySlot(EquipmentSlot.MAINHAND);

            if (itemStack.isEmpty())
            {
                for (int i = 0; i < 8; i++)
                {
                    Vec3 vec3 = (new Vec3(((double)this.random.nextFloat() - (double)0.5F) * 0.1, Math.random() * 0.1 + 0.1, (double)0.0F)).xRot(-this.getXRot() * ((float)Math.PI / 180F)).yRot(-this.getYRot() * ((float)Math.PI / 180F));
                    this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, itemStack.getItem()), this.getX() + this.getLookAngle().x / (double)2.0F, this.getY(), this.getZ() + this.getLookAngle().z / (double)2.0F, vec3.x, vec3.y + 0.05, vec3.z);
                }
            }
        }
        else
        {
            super.handleEntityEvent(status);
        }
    }

    @Override
    public void travel(Vec3 movementInput)
    {
        if (this.isInWater())
        {
            this.moveRelative(0.01f, movementInput);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.8f, 0.8f, 0.8f));

            Vec3 velocity = this.getDeltaMovement();
            double horizontalSpeed = Math.sqrt(velocity.x * velocity.y + velocity.z);
            float pitch = (float)(-Mth.atan2(velocity.y, horizontalSpeed) * (180F / Math.PI));
            this.setXRot(Mth.clamp(pitch, 15.0F, 165.0F));
        }
        else
        {
            super.travel(movementInput);
        }
    }

    @Override
    public boolean doHurtTarget(ServerLevel level, Entity target)
    {
        boolean success = super.doHurtTarget(level, target);

        if (success && target instanceof LivingEntity)
        {
            this.setLastHurtMob(target);
        }

        return success;
    }


    @Override
    public boolean canBreatheUnderwater() { return true; }

    @Override
    public int getMaxAirSupply() { return 300; }

    @Override
    public boolean isFood(ItemStack itemStack) { return itemStack.is(ModTags.Items.PENGUIN_FOODS); }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob entity)
    {
        return ModEntities.PENGUIN.create(level, EntitySpawnReason.BREEDING);
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() { return ModSounds.PENGUIN_AMBIENT; }

    @Override
    protected @Nullable SoundEvent getDeathSound() { return super.getDeathSound(); }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) { this.playSound(SoundEvents.CHICKEN_STEP.value(), 0.15F, 1.0F); }

    @Override
    protected void playSwimSound(float volume) { super.playSwimSound(volume * 1.5F); }

    @Override
    protected @NonNull SoundEvent getSwimSound() { return SoundEvents.GENERIC_SWIM; }

    @Override
    protected PathNavigation createNavigation(Level level)
    {
        return new PenguinSwimNavigation(this, level);
    }

    static
    {
        PICKABLE_DROP_FILTER = (item) -> !item.hasPickUpDelay() && item.isAlive() && item.getItem().is(ModTags.Items.PENGUIN_FOODS);
    }

    public boolean canPickupItem(ItemStack stack)
    {
        ItemStack equippedItemStack = this.getItemBySlot(EquipmentSlot.MAINHAND);

        return equippedItemStack.isEmpty() || this.eatingTime > 0 && stack.has(DataComponents.FOOD) && !equippedItemStack.has(DataComponents.FOOD) && equippedItemStack.is(ModTags.Items.PENGUIN_FOODS);
    }

    private void spit(ItemStack stack)
    {
        if (!stack.isEmpty() && !this.level().isClientSide())
        {
            ItemEntity itemEntity = new ItemEntity(this.level(), this.getX() + this.getLookAngle().x, this.getY() + (double) 1.0F, this.getZ() + this.getLookAngle().z, stack);
            itemEntity.setPickUpDelay(40);
            itemEntity.setThrower(this);
            this.playSound(SoundEvents.FOX_SPIT, 1.0F, 1.0F);
            this.level().addFreshEntity(itemEntity);
        }
    }

    private void dropItem(ItemStack stack)
    {
        ItemEntity itemEntity = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), stack);
        this.level().addFreshEntity(itemEntity);
    }

    @Override
    protected void pickUpItem(final ServerLevel level, final ItemEntity itemEntity)
    {
        ItemStack itemStack = itemEntity.getItem();

        if (this.canPickupItem(itemStack))
        {
            int i = itemStack.getCount();

            if (i > 1)
            {
                this.dropItem(itemStack.split(i - 1));
            }

            if (!this.level().isClientSide())
            {
                System.out.println("Server: Penguin looted item " + itemStack + " at tick " + this.age);
                System.out.println("Thread: " + Thread.currentThread().getName());
            }

            this.spit(this.getItemBySlot(EquipmentSlot.MAINHAND));
            this.onItemPickup(itemEntity);
            this.setItemSlot(EquipmentSlot.MAINHAND, itemStack.split(1));
            this.setGuaranteedDrop(EquipmentSlot.MAINHAND);
            this.take(itemEntity, itemStack.getCount());
            itemEntity.discard();
            this.eatingTime = 0;
        }
        else
        {
            super.pickUpItem(level, itemEntity);
        }
    }

    @Override
    protected void dropAllDeathLoot(final ServerLevel level, final DamageSource source)
    {
        ItemStack itemStack = this.getItemBySlot(EquipmentSlot.MAINHAND);

        if (!itemStack.isEmpty())
        {
            this.spawnAtLocation(level, itemStack);
            this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        }

        super.dropAllDeathLoot(level, source);
    }

    public class PenguinPickupItemGoal extends Goal
    {
        Predicate<ItemEntity> itemFilter;
        double speedModifier = 1.0D;
        double range = 8.0D;

        public PenguinPickupItemGoal(Predicate<ItemEntity> itemFilter, double range, double speedModifier)
        {
            Objects.requireNonNull(Penguin.this);
            super();
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));

            this.itemFilter = itemFilter;
            this.speedModifier = speedModifier;
            this.range = range;
        }

        public boolean canUse()
        {
            if (!Penguin.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty())
            {
                return false;
            }
            else if (Penguin.this.getTarget() == null && Penguin.this.getLastHurtByMob() == null)
            {
                if (Penguin.this.getRandom().nextInt(reducedTickDelay(10)) != 0)
                {
                    return false;
                }
                else
                {
                    List<ItemEntity> list = Penguin.this.level().getEntitiesOfClass(ItemEntity.class, Penguin.this.getBoundingBox().inflate(range), itemFilter);
                    return !list.isEmpty() && Penguin.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty();
                }
            }
            else
            {
                return false;
            }
        }

        public void tick()
        {
            List<ItemEntity> list = Penguin.this.level().getEntitiesOfClass(ItemEntity.class, Penguin.this.getBoundingBox().inflate(range), itemFilter);

            ItemStack itemStack = Penguin.this.getItemBySlot(EquipmentSlot.MAINHAND);

            if (itemStack.isEmpty() && !list.isEmpty())
            {
                Penguin.this.getNavigation().moveTo((Entity)list.get(0), speedModifier);
            }
        }

        public void start()
        {
            List<ItemEntity> list = Penguin.this.level().getEntitiesOfClass(ItemEntity.class, Penguin.this.getBoundingBox().inflate(range), itemFilter);

            if (!list.isEmpty())
            {
                Penguin.this.getNavigation().moveTo((Entity)list.get(0), speedModifier);
            }
        }
    }
}
