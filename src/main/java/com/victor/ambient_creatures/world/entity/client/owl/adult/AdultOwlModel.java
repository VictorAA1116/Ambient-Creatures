package com.victor.ambient_creatures.world.entity.client.owl.adult;

import com.victor.ambient_creatures.world.entity.client.owl.OwlModel;
import com.victor.ambient_creatures.world.entity.client.owl.OwlRenderState;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class AdultOwlModel extends OwlModel
{
    private final KeyframeAnimation idleAnimation;
    private final KeyframeAnimation walkingAnimation;
    private final KeyframeAnimation flyingAnimation;

    public AdultOwlModel(ModelPart root)
    {
        super(root);

        this.idleAnimation = AdultOwlAnimations.IDLE.bake(root);
        this.walkingAnimation = AdultOwlAnimations.WALKING.bake(root);
        this.flyingAnimation = AdultOwlAnimations.FLYING.bake(root);
    }

    public static LayerDefinition getTexturedModelData()
    {
        // Root
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartDefinition= modelData.getRoot();
        PartDefinition root = modelPartDefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        // Body
        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(-0.5F, -5.75F, 0.0F));

        PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -5.0F, -3.0F, 6.0F, 8.0F, 5.0F), PartPose.offsetAndRotation(-0.5F, 0.0F, 1.0F, 1.0036F, 0.0F, 0.0F));

        // Head
        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 14).addBox(-3.0F, -3.5F, -2.5F, 6.0F, 6.0F, 5.0F), PartPose.offset(0.5F, -4.75F, -2.0F));

        // Nose
        PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(13, 26).addBox(-1.0F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F), PartPose.offset(0.5F, 1.5F, -3.0F));

        // Left Wing
        PartDefinition left_wing = body.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(21, 22).addBox(0.0F, 0.0F, -2.5F, 1.0F, 10.0F, 5.0F)
                .texOffs(26, 50).addBox(1.0F, 10.0F, -2.5F, 0.0F, 3.0F, 5.0F), PartPose.offsetAndRotation(3.5F, -2.5F, -2.75F, 1.0036F, 0.0F, 0.0F));

        // Right Wing
        PartDefinition right_wing = body.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(0, 26).addBox(-1.0F, 0.0F, -2.5F, 1.0F, 10.0F, 5.0F)
                .texOffs(26, 50).addBox(-1.0F, 10.0F, -2.5F, 0.0F, 3.0F, 5.0F), PartPose.offsetAndRotation(-2.5F, -2.5F, -2.75F, 1.0036F, 0.0F, 0.0F));

        // Left Leg
        PartDefinition left_leg = body.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(23, 8).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F), PartPose.offset(2.0F, 2.75F, 1.5F));
        PartDefinition left_foot = left_leg.addOrReplaceChild("left_foot", CubeListBuilder.create().texOffs(23, 0).addBox(0.0F, -1.0F, -1.5F, 3.0F, 0.0F, 3.0F), PartPose.offset(-1.5F, 4.0F, -1.0F));

        // Right Leg
        PartDefinition right_leg = body.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(32, 8).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F), PartPose.offset(-1.0F, 2.75F, 1.5F));
        PartDefinition right_foot = right_leg.addOrReplaceChild("right_foot", CubeListBuilder.create().texOffs(23, 4).addBox(0.0F, -1.0F, -1.5F, 3.0F, 0.0F, 3.0F), PartPose.offset(-1.5F, 4.0F, -1.0F));

        // Tail
        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(28, 16).addBox(-2.5F, -0.5F, 0.0F, 5.0F, 1.0F, 4.0F)
                .texOffs(34, 24).addBox(-2.5F, -0.5F, 4.0F, 5.0F, 0.0F, 3.0F), PartPose.offset(0.5F, 0.25F, 4.25F));

        return LayerDefinition.create(modelData, 64, 64);
    }

    @Override
    public void setupAnim(OwlRenderState state)
    {
        super.setupAnim(state);

        if (state.flyingAnimationState.isStarted())
        {
            this.flyingAnimation.apply(state.flyingAnimationState, state.ageInTicks);
        }
        else if (state.walkingAnimationState.isStarted())
        {
            float limbSwingAmplitude = state.walkAnimationSpeed * 1.5f;
            float limbSwingAnimationProgress = state.walkAnimationPos;

            float limbSwingSpeed = 3.0F;
            float limbSwingAmount = 2.5f;

            this.walkingAnimation.applyWalk(limbSwingAnimationProgress, limbSwingAmplitude, limbSwingSpeed, limbSwingAmount);
        }
        else if (state.idleAnimationState.isStarted())
        {
            this.idleAnimation.apply(state.idleAnimationState, state.ageInTicks);
        }
    }
}
