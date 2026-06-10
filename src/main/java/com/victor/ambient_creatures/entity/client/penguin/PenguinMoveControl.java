package com.victor.ambient_creatures.entity.client.penguin;

import com.victor.ambient_creatures.entity.custom.Penguin;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.Vec3;

public class PenguinMoveControl extends MoveControl
{
    private final Penguin penguin;

    public PenguinMoveControl(Penguin penguin) {
        super(penguin);
        this.penguin = penguin;
    }

    private void updateVelocity() {
        if(!this.penguin.isInWater()) {
            if (this.penguin.onGround()) {
                this.penguin.setSpeed(Math.max(this.penguin.getSpeed() / 2.0F, 0.06F));
            }
        }

    }

    public void tick()
    {
        this.updateVelocity();

        if (this.operation == Operation.MOVE_TO && !this.penguin.getNavigation().isDone())
        {
            double distX = this.wantedX - this.penguin.getX();
            double distY = this.wantedY - this.penguin.getY();
            double distZ = this.wantedZ - this.penguin.getZ();
            double distance = Math.sqrt(distX * distX + distY * distY + distZ * distZ);

            if (distance < 0.1)
            {
                this.penguin.setSpeed(0.0F);
            }
            else
            {
                distY /= distance;
                float targetYaw = (float) (Mth.atan2(distZ, distX) * (double) (180F / (float) Math.PI)) - 90.0F;
                this.penguin.setYRot(this.rotlerp(this.penguin.getYRot(), targetYaw, 90.0F));
                this.penguin.yBodyRot = this.penguin.getYRot();

                float speed = (float) (this.speedModifier * this.penguin.getAttributeValue(Attributes.MOVEMENT_SPEED) / 2.5F);
                this.penguin.setSpeed(speed);
                this.penguin.setDeltaMovement(this.penguin.getDeltaMovement().add((double)0.0F, (double)this.penguin.getSpeed() * distY * 0.1, (double)0.0F));
            }

            if (this.penguin.isInWater())
            {
                Vec3 direction = new Vec3(distX, distY, distZ).normalize().multiply(0.05, 0.05, 0.05);
                this.penguin.setDeltaMovement(this.penguin.getDeltaMovement().add(direction));
            }
        }
        else
        {
            this.penguin.setSpeed(0.0F);
        }
    }
}
