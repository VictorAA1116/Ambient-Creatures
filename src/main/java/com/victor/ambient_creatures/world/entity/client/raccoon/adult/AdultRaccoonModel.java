package com.victor.ambient_creatures.world.entity.client.raccoon.adult;

import com.victor.ambient_creatures.world.entity.client.raccoon.RaccoonModel;
import com.victor.ambient_creatures.world.entity.client.raccoon.RaccoonRenderState;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import org.jspecify.annotations.Nullable;

public class AdultRaccoonModel extends RaccoonModel
{
    private final KeyframeAnimation idleAnimation;
    private final KeyframeAnimation walkingAnimation;
    private final KeyframeAnimation sleepingAnimation;
    private final KeyframeAnimation standingAnimation;

    public AdultRaccoonModel(ModelPart root)
    {
        super(root);

        this.idleAnimation = AdultRaccoonAnimations.IDLE.bake(root);
        this.walkingAnimation = AdultRaccoonAnimations.WALKING.bake(root);
        this.sleepingAnimation = AdultRaccoonAnimations.SLEEPING.bake(root);
        this.standingAnimation = AdultRaccoonAnimations.STANDING.bake(root);
    }

    public static LayerDefinition getTexturedModelData()
    {
        // Root
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartDefinition = modelData.getRoot();
        PartDefinition root = modelPartDefinition.addOrReplaceChild(PartNames.ROOT, CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        // Body
        PartDefinition body = root.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create().texOffs(0, 0).addBox(-4.5F, -4.0F, -6.0F, 9.0F, 6.0F, 12.0F), PartPose.offset(0.0F, -5.0F, 0.0F));

        // Head
        PartDefinition head = body.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create().texOffs(23, 19).addBox(-3.5F, -2.0F, -4.0F, 7.0F, 4.0F, 5.0F), PartPose.offset(0.0F, -3.0F, -6.0F));

        // Ears
        PartDefinition left_ear = head.addOrReplaceChild(PartNames.LEFT_EAR, CubeListBuilder.create(), PartPose.offset(-2.0F, -1.5F, -0.5F));
        left_ear.addOrReplaceChild("left_ear_r1", CubeListBuilder.create().texOffs(38, 29).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 2.0F, 1.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3927F));

        PartDefinition right_ear = head.addOrReplaceChild(PartNames.RIGHT_EAR, CubeListBuilder.create(), PartPose.offset(2.0F, -1.5F, -0.5F));
        right_ear.addOrReplaceChild("right_ear_r1", CubeListBuilder.create().texOffs(38, 29).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 2.0F, 1.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

        // Nose
        head.addOrReplaceChild(PartNames.NOSE, CubeListBuilder.create().texOffs(24, 29).addBox(-1.5F, -1.0F, -2.0F, 3.0F, 2.0F, 2.0F), PartPose.offset(0.0F, 1.0F, -4.0F));

        // Left Front Leg
        body.addOrReplaceChild(PartNames.LEFT_FRONT_LEG, CubeListBuilder.create().texOffs(0, 31).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F), PartPose.offset(2.5F, 2.0F, -4.0F));

        // Right Front Leg
        body.addOrReplaceChild(PartNames.RIGHT_FRONT_LEG, CubeListBuilder.create().texOffs(0, 31).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F), PartPose.offset(-2.5F, 2.0F, -4.0F));

        // Left Hind Leg
        body.addOrReplaceChild(PartNames.LEFT_HIND_LEG, CubeListBuilder.create().texOffs(9, 31).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F), PartPose.offset(2.5F, 2.0F, 5.0F));

        // Right Hind Leg
        body.addOrReplaceChild(PartNames.RIGHT_HIND_LEG, CubeListBuilder.create().texOffs(9, 31).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F), PartPose.offset(-2.5F, 2.0F, 5.0F));

        // Tail
        body.addOrReplaceChild(PartNames.TAIL, CubeListBuilder.create().texOffs(0, 19).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 7.0F), PartPose.offset(0.0F, -1.0F, 6.0F));

        return LayerDefinition.create(modelData, 64, 64);
    }

    @Override
    public void setupAnim(RaccoonRenderState state)
    {
        super.setupAnim(state);

        if (state.sleepingAnimationState.isStarted())
        {
            this.sleepingAnimation.apply(state.sleepingAnimationState, state.ageInTicks);
        }
        else
        {
            if (state.idleAnimationState.isStarted())
            {
                this.idleAnimation.apply(state.idleAnimationState, state.ageInTicks);
            }

            if (state.standingAnimationState.isStarted())
            {
                this.standingAnimation.apply(state.standingAnimationState, state.ageInTicks);
            }

            if (state.walkingAnimationState.isStarted())
            {
                float limbSwingAmplitude = state.walkAnimationSpeed * 1.5f;
                float limbSwingAnimationProgress = state.walkAnimationPos;

                float limbSwingSpeed = 3.0F;
                float limbSwingAmount = 2.5f;

                this.walkingAnimation.applyWalk(limbSwingAnimationProgress, limbSwingAmplitude, limbSwingSpeed, limbSwingAmount);
            }
        }
    }
}
