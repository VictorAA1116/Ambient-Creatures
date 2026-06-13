package com.victor.ambient_creatures.world.entity.client.penguin;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.util.Mth;

public class PenguinModel extends EntityModel<PenguinRenderState>
{
    private final ModelPart root;
    public final ModelPart head;
    public final ModelPart body;
    private final ModelPart rightWing;
    private final ModelPart leftWing;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;
    private final ModelPart tail;

    protected PenguinModel(ModelPart root)
    {
        super(root);

        this.root = root.getChild(PartNames.ROOT);
        this.body = this.root.getChild(PartNames.BODY);
        this.head = this.body.getChild(PartNames.HEAD);
        this.rightWing = this.body.getChild(PartNames.RIGHT_WING);
        this.leftWing = this.body.getChild(PartNames.LEFT_WING);
        this.rightLeg = this.body.getChild(PartNames.RIGHT_LEG);
        this.leftLeg = this.body.getChild(PartNames.LEFT_LEG);
        this.tail = this.body.getChild(PartNames.TAIL);
    }



    @Override
    public void setupAnim(PenguinRenderState state)
    {
        super.setupAnim(state);
        this.resetPose();

        // Only add to body pitch when touching water (not for sliding)
        if (state.touchingWater)
        {
            this.body.xRot += state.xRot * (float)(Math.PI / 180.0F);
        }

        boolean shouldBeLayingDown = state.slidingAnimationState.isStarted() || state.swimmingAnimationState.isStarted() || state.swimIdleAnimationState.isStarted();

        this.setHeadAngles(state.yRot, state.xRot, shouldBeLayingDown);
    }

    private void setHeadAngles(float headYaw, float headPitch, boolean isLayingDown)
    {
        headYaw = Mth.clamp(headYaw, -30.0f, 30.0f);
        headPitch = Mth.clamp(headPitch, -25.0f, 45.0f);

//        if (isLayingDown)
//        {
//            headPitch -= 90f;
//        }

        this.head.xRot = headPitch * (float)(Math.PI / 180.0F);

        if (isLayingDown)
        {
            this.head.zRot = -(headYaw * 0.017453292F);
        }
        else
        {
            this.head.yRot = headYaw * 0.017453292F;
        }
    }
}
