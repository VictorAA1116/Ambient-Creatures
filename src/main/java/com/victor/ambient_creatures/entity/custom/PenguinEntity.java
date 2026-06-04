package com.victor.ambient_creatures.entity.custom;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.fish.Cod;
import net.minecraft.world.entity.animal.fish.Pufferfish;
import net.minecraft.world.entity.animal.fish.Salmon;
import net.minecraft.world.entity.animal.fish.TropicalFish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.w3c.dom.Attr;

import java.util.EnumSet;
import java.util.List;

public class PenguinEntity extends Animal
{
    public PenguinEntity(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 15)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
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
        this.goalSelector.addGoal(4, new TemptGoal(this, 1, Ingredient.of(Items.SALMON), false));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1, Ingredient.of(Items.COD), false));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1, Ingredient.of(Items.TROPICAL_FISH), false));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(6, new RandomStrollGoal(this, 1));
        this.goalSelector.addGoal(7, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(8, new RandomSwimmingGoal(this, 1, 1));
        //this.goalSelector.addGoal(9, new PickupItemGoal(this));
        this.goalSelector.addGoal(10, new MeleeAttackGoal(this, 1.5, true));
        this.goalSelector.addGoal(11, new LookAtPlayerGoal(this, Player.class, 4));
        this.goalSelector.addGoal(12, new RandomLookAroundGoal(this));
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return this.isConsumableFood(itemStack) && this.getTarget() == null;
    }

    private boolean isConsumableFood(ItemStack itemStack)
    {
        return itemStack.has(DataComponents.FOOD) && itemStack.has(DataComponents.CONSUMABLE);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob partner) {
        return null;
    }

//    public class PickupItemGoal extends Goal {
//        public PickupItemGoal(PenguinEntity penguin) {
//            this.setControls(EnumSet.of(Control.MOVE));
//        }
//
//        public boolean canUse() {
//            if (PenguinEntity.this.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty()){
//                return false;
//            } else if (PenguinEntity.this.getTarget() == null && PenguinEntity.this.getAttacker() == null){
//                if (!PenguinEntity.this.getNavigation().isIdle()) {
//                    return false;
//                } else if (PenguinEntity.this.getRandom().nextInt(toGoalTicks(10)) != 0) {
//                    return false;
//                } else {
//                    List<ItemEntity> list = PenguinEntity.this.getEntityWorld().getEntitiesByClass(ItemEntity.class, PenguinEntity.this.getBoundingBox().expand((double)8.0F, (double)8.0F, (double)8.0F), PenguinEntity.PICKABLE_DROP_FILTER);
//                    return !list.isEmpty() && PenguinEntity.this.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty();
//                }
//            } else {
//                return false;
//            }
//        }
//
//        public void tick() {
//            List<ItemEntity> list = PenguinEntity.this.getEntityWorld().getEntitiesByClass(ItemEntity.class, PenguinEntity.this.getBoundingBox().expand((double)8.0F, (double)8.0F, (double)8.0F), PenguinEntity.PICKABLE_DROP_FILTER);
//            ItemStack itemStack = PenguinEntity.this.getEquippedStack(EquipmentSlot.MAINHAND);
//            if (itemStack.isEmpty() && !list.isEmpty()) {
//                PenguinEntity.this.getNavigation().startMovingTo((Entity)list.get(0), (double)1.2F);
//            }
//        }
//
//        public void start() {
//            List<ItemEntity> list = PenguinEntity.this.getEntityWorld().getEntitiesByClass(ItemEntity.class, PenguinEntity.this.getBoundingBox().expand((double)8.0F, (double)8.0F, (double)8.0F), PenguinEntity.PICKABLE_DROP_FILTER);
//            if (!list.isEmpty()) {
//                PenguinEntity.this.getNavigation().startMovingTo((Entity)list.get(0), (double)1.2F);
//            }
//
//        }
//    }
}
