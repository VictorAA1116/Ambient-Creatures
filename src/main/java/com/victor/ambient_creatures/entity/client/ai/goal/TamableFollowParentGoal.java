package com.victor.ambient_creatures.entity.client.ai.goal;

import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Animal;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class TamableFollowParentGoal extends Goal
{
//    public static final int DONT_FOLLOW_IF_CLOSER_THAN = 3;

    private final Animal animal;
    @Nullable
    private Animal parent;
    private final double speedModifier;

    private double horizontalScanRange = 8;
    private double verticalScanRange = 4;

    private int timeToRecalcPath;

    public TamableFollowParentGoal(final Animal animal, final double speedModifier)
    {
        this.animal = animal;
        this.speedModifier = speedModifier;
    }

    public TamableFollowParentGoal(final Animal animal, final double speedModifier, final double horizontalScanRange, final double verticalScanRange)
    {
        this.animal = animal;
        this.speedModifier = speedModifier;
        this.horizontalScanRange = horizontalScanRange;
        this.verticalScanRange = verticalScanRange;
    }

    @Override
    public boolean canUse()
    {
        if (this.animal.getAge() >= 0)
        {
            return false;
        }
        else if (this.animal instanceof TamableAnimal
                && ((TamableAnimal)this.animal).isTame()
                && (((TamableAnimal) this.animal).isOrderedToSit() || ((TamableAnimal) this.animal).isInSittingPose()))
        {
            return false;
        }
        else
        {
            List<? extends Animal> parents = this.animal.level().getEntitiesOfClass(this.animal.getClass(), this.animal.getBoundingBox().inflate(8.0, verticalScanRange, horizontalScanRange));

            Animal closest = null;

            double closestDistSqr = Double.MAX_VALUE;

            for (Animal parent : parents)
            {
                if (parent.getAge() >= 0)
                {
                    double distSqr = this.animal.distanceToSqr(parent);

                    if (!(distSqr > closestDistSqr))
                    {
                        closestDistSqr = distSqr;
                        closest = parent;
                    }
                }
            }

            if (closest == null)
            {
                return false;
            }
            else if (closestDistSqr < 9.0)
            {
                return false;
            }
            else
            {
                this.parent = closest;
                return true;
            }
        }
    }

    @Override
    public boolean canContinueToUse()
    {
        if (this.animal.getAge() >= 0)
        {
            return false;
        }
        else if (!this.parent.isAlive())
        {
            return false;
        }
        else
        {
            double distSqr = this.animal.distanceToSqr(this.parent);
            return !(distSqr < 9.0) && !(distSqr > 256.0);
        }
    }

    @Override
    public void start() {
        this.timeToRecalcPath = 0;
    }

    @Override
    public void stop() {
        this.parent = null;
    }

    @Override
    public void tick()
    {
        if (--this.timeToRecalcPath <= 0)
        {
            this.timeToRecalcPath = this.adjustedTickDelay(10);
            this.animal.getNavigation().moveTo(this.parent, this.speedModifier);
        }
    }
}
