package com.victor.ambient_creatures.entity.client.capybara;

import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class CapybaraModel extends EntityModel<CapybaraRenderState>
{
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart left_ear;
    private final ModelPart right_ear;
    private final ModelPart left_front_leg;
    private final ModelPart right_front_leg;
    private final ModelPart left_hind_leg;
    private final ModelPart right_hind_leg;

    private final KeyframeAnimation idleAnimation;
    private final KeyframeAnimation walkingAnimation;
    private final KeyframeAnimation sittingAnimation;

    protected CapybaraModel(ModelPart root)
    {
        super(root);

        this.root = root.getChild(PartNames.ROOT);
        this.body = this.root.getChild(PartNames.BODY);
        this.head = this.body.getChild(PartNames.HEAD);
        this.left_ear = this.head.getChild(PartNames.LEFT_EAR);
        this.right_ear = this.head.getChild(PartNames.RIGHT_EAR);
        this.left_front_leg = this.body.getChild(PartNames.LEFT_FRONT_LEG);
        this.right_front_leg = this.body.getChild(PartNames.RIGHT_FRONT_LEG);
        this.left_hind_leg = this.body.getChild(PartNames.LEFT_HIND_LEG);
        this.right_hind_leg = this.body.getChild(PartNames.RIGHT_HIND_LEG);

        this.idleAnimation = CapybaraAnimations.IDLE.bake(root);
        this.walkingAnimation = CapybaraAnimations.WALKING.bake(root);
        this.sittingAnimation = CapybaraAnimations.SITTING.bake(root);
    }

    public static LayerDefinition getTexturedModelData()
    {
        // Root
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartDefinition = modelData.getRoot();
        PartDefinition root = modelPartDefinition.addOrReplaceChild(PartNames.ROOT, CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        // Body
        PartDefinition body = root.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -5.0F, -9.0F, 10.0F, 10.0F, 18.0F), PartPose.offset(0.0F, -11.0F, 0.0F));

        // Head
        PartDefinition head = body.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create().texOffs(0, 29).addBox(-3.0F, -4.2685F, -1.3612F, 6.0F, 6.0F, 10.0F), PartPose.offsetAndRotation(0.0F, -4.0F, 7.0F, -0.1745F, 0.0F, 0.0F));

        // Left Ear
        PartDefinition left_ear = head.addOrReplaceChild(PartNames.LEFT_EAR, CubeListBuilder.create(), PartPose.offsetAndRotation(-2.0F, -4.2685F, 0.1388F, 0.0F, -0.5236F, 0.0F));
        left_ear.addOrReplaceChild("ear_r1", CubeListBuilder.create().texOffs(38, 30).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 1.0F), PartPose.offsetAndRotation(0.3F, 0.3F, 0.5F, 0.0F, 0.0F, -0.2618F));

        // Right Ear
        PartDefinition right_ear = head.addOrReplaceChild(PartNames.RIGHT_EAR, CubeListBuilder.create(), PartPose.offsetAndRotation(2.0F, -4.2685F, 0.1388F, 0.0F, 0.5236F, 0.0F));
        right_ear.addOrReplaceChild("ear_r2", CubeListBuilder.create().texOffs(38, 34).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 1.0F), PartPose.offsetAndRotation(-0.3F, 0.3F, 0.5F, 0.0F, 0.0F, 0.2618F));

        // Left Front Leg
        body.addOrReplaceChild(PartNames.LEFT_FRONT_LEG, CubeListBuilder.create().texOffs(3, 4).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F), PartPose.offset(-3.0F, 5.0F, 6.5F));

        // Right Front Leg
        body.addOrReplaceChild(PartNames.RIGHT_FRONT_LEG, CubeListBuilder.create().texOffs(3, 4).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F), PartPose.offset(3.0F, 5.0F, 6.5F));

        // Left Hind Leg
        body.addOrReplaceChild(PartNames.LEFT_HIND_LEG, CubeListBuilder.create().texOffs(40, 4).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F), PartPose.offset(-3.0F, 5.0F, -8.0F));

        // Right Hind Leg
        body.addOrReplaceChild(PartNames.RIGHT_HIND_LEG, CubeListBuilder.create().texOffs(40, 4).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F), PartPose.offset(3.0F, 5.0F, -8.0F));

        return LayerDefinition.create(modelData, 64, 64);
    }

    @Override
    public void setupAnim(CapybaraRenderState state)
    {
        super.setupAnim(state);
        this.setHeadAngles(state.yRot, state.xRot);

        if (state.walkingAnimationState.isStarted())
        {
            this.walkingAnimation.applyWalk(state.walkAnimationPos, state.walkAnimationSpeed * 1.5F, 3.0F, 10.0F);
        }
        else if (state.idleAnimationState.isStarted())
        {
            this.idleAnimation.apply(state.idleAnimationState, state.ageInTicks);
        }
        else if (state.sittingAnimationState.isStarted())
        {
            this.sittingAnimation.apply(state.sittingAnimationState, state.ageInTicks);
        }
    }

    private void setHeadAngles(float headYaw, float headPitch) {
        headYaw = Mth.clamp(headYaw, -30.0f, 30.0f);
        headPitch = Mth.clamp(headPitch, -25.0f, 45.0f);

        this.head.yRot = headYaw * 0.017453292F;
        this.head.xRot = headPitch * (float) -(Math.PI / 180.0);
    }
}
