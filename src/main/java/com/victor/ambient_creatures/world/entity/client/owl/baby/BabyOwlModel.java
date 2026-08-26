package com.victor.ambient_creatures.world.entity.client.owl.baby;

import com.victor.ambient_creatures.world.entity.client.owl.OwlModel;
import com.victor.ambient_creatures.world.entity.client.owl.OwlRenderState;
import com.victor.ambient_creatures.world.entity.client.owl.adult.AdultOwlAnimations;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class BabyOwlModel extends OwlModel
{
    private final KeyframeAnimation idleAnimation;
    private final KeyframeAnimation walkingAnimation;
    private final KeyframeAnimation flyingAnimation;

    public BabyOwlModel(ModelPart root)
    {
        super(root);

        this.idleAnimation = BabyOwlAnimations.IDLE.bake(root);
        this.walkingAnimation = BabyOwlAnimations.WALKING.bake(root);
        this.flyingAnimation = BabyOwlAnimations.FLYING.bake(root);
    }

    public static LayerDefinition getTexturedModelData()
    {
        // Root
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartDefinition = modelData.getRoot();
        PartDefinition root = modelPartDefinition.addOrReplaceChild(PartNames.ROOT, CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        // Body
        PartDefinition body = root.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create(), PartPose.offset(-0.5F, -2.75F, 0.0F));

        PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 8).addBox(-1.0F, -5.0F, -1.0F, 4.0F, 6.0F, 3.0F), PartPose.offsetAndRotation(-0.5F, 0.0F, 1.0F, 1.0036F, 0.0F, 0.0F));

        // Head
        PartDefinition head = body.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.5F, -2.5F, 4.0F, 4.0F, 4.0F), PartPose.offset(0.5F, -4.75F, -2.0F));

        // Nose
        PartDefinition nose = head.addOrReplaceChild(PartNames.NOSE, CubeListBuilder.create().texOffs(16, 19).addBox(-1.0F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F), PartPose.offset(0.5F, 1.5F, -3.0F));

        // Left Wing
        PartDefinition left_wing = body.addOrReplaceChild(PartNames.LEFT_WING, CubeListBuilder.create().texOffs(14, 8).addBox(0.0F, 0.2108F, -1.3657F, 1.0F, 6.0F, 3.0F)
                .texOffs(9, 17).addBox(1.0F, 6.21F, -1.365F, 0.0F, 2.0F, 3.0F), PartPose.offsetAndRotation(2.5F, -3.5F, -2.75F, 1.0036F, 0.0F, 0.0F));

        // Right Wing
        PartDefinition right_wing = body.addOrReplaceChild(PartNames.RIGHT_WING, CubeListBuilder.create().texOffs(0, 17).addBox(-1.0F, 0.2108F, -1.3657F, 1.0F, 6.0F, 3.0F)
                .texOffs(9, 17).addBox(-1.0F, 6.2108F, -1.3657F, 0.0F, 2.0F, 3.0F), PartPose.offsetAndRotation(-1.5F, -3.5F, -2.75F, 1.0036F, 0.0F, 0.0F));

        // Left Leg
        PartDefinition left_leg = body.addOrReplaceChild(PartNames.LEFT_LEG, CubeListBuilder.create().texOffs(10, 23).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F), PartPose.offset(2.0F, 0.75F, 0.5F));
        PartDefinition left_foot = left_leg.addOrReplaceChild(PartNames.LEFT_FOOT, CubeListBuilder.create().texOffs(8, 17).addBox(0.0F, -2.0F, -0.5F, 2.0F, 0.0F, 2.0F), PartPose.offset(-1.5F, 4.0F, -1.0F));

        // Right Leg
        PartDefinition right_leg = body.addOrReplaceChild(PartNames.RIGHT_LEG, CubeListBuilder.create().texOffs(10, 23).addBox(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F), PartPose.offset(-1.0F, 0.75F, 0.5F));
        PartDefinition right_foot = right_leg.addOrReplaceChild(PartNames.RIGHT_FOOT, CubeListBuilder.create().texOffs(8, 17).addBox(1.0F, -2.0F, -0.5F, 2.0F, 0.0F, 2.0F), PartPose.offset(-1.5F, 4.0F, -1.0F));

        // Tail
        PartDefinition tail = body.addOrReplaceChild(PartNames.TAIL, CubeListBuilder.create().texOffs(16, 0).addBox(-1.5F, -0.5F, 0.0F, 3.0F, 1.0F, 2.0F)
                .texOffs(16, 3).addBox(-1.5F, -0.5F, 2.0F, 3.0F, 0.0F, 2.0F), PartPose.offset(0.5F, -0.75F, 2.5F));

        return LayerDefinition.create(modelData, 32, 32);
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
