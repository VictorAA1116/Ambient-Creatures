package com.victor.ambient_creatures.world.entity.client.raccoon;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.util.Mth;

public class RaccoonModel extends EntityModel<RaccoonRenderState>
{
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart nose;
    private final ModelPart leftEar;
    private final ModelPart rightEar;
    private final ModelPart leftFrontLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart rightHindLeg;
    private final ModelPart tail;

    protected RaccoonModel(ModelPart root)
    {
        super(root);

        this.root = root.getChild(PartNames.ROOT);
        this.body = this.root.getChild(PartNames.BODY);
        this.head = this.body.getChild(PartNames.HEAD);
        this.nose = this.head.getChild(PartNames.NOSE);
        this.leftEar = this.head.getChild(PartNames.LEFT_EAR);
        this.rightEar = this.head.getChild(PartNames.RIGHT_EAR);
        this.leftFrontLeg = this.body.getChild(PartNames.LEFT_FRONT_LEG);
        this.rightFrontLeg = this.body.getChild(PartNames.RIGHT_FRONT_LEG);
        this.leftHindLeg = this.body.getChild(PartNames.LEFT_HIND_LEG);
        this.rightHindLeg = this.body.getChild(PartNames.RIGHT_HIND_LEG);
        this.tail = this.body.getChild(PartNames.TAIL);
    }

    @Override
    public void setupAnim(RaccoonRenderState state)
    {
        super.setupAnim(state);
        setHeadAngles(state.yRot, state.xRot, state.standingAnimationState.isStarted());
    }

    private void setHeadAngles(float headYaw, float headPitch, boolean isStanding)
    {
        if (isStanding)
        {
            headPitch += 25;
            this.head.zRot = headYaw * 0.5f * (float)(Math.PI / 180.0);
            this.head.yRot = headYaw * 0.5f * (float)(Math.PI / 180.0);
        }
        else
        {
            this.head.yRot = headYaw * (float)(Math.PI / 180.0);
        }

        this.head.xRot = headPitch * (float)(Math.PI / 180.0);
    }
}
