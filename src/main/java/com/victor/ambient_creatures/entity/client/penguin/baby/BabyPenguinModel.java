package com.victor.ambient_creatures.entity.client.penguin.baby;

import com.victor.ambient_creatures.entity.client.penguin.PenguinModel;
import com.victor.ambient_creatures.entity.client.penguin.PenguinRenderState;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class BabyPenguinModel extends PenguinModel
{
    private final KeyframeAnimation idleAnimation;
    private final KeyframeAnimation walkingAnimation;
    private final KeyframeAnimation swimIdleAnimation;
    private final KeyframeAnimation swimmingAnimation;
    private final KeyframeAnimation slidingAnimation;

    public BabyPenguinModel(ModelPart root)
    {
        super(root);

        this.idleAnimation = BabyPenguinAnimations.IDLE.bake(root);
        this.walkingAnimation = BabyPenguinAnimations.WALKING.bake(root);
        this.swimIdleAnimation = BabyPenguinAnimations.SWIM_IDLE.bake(root);
        this.swimmingAnimation = BabyPenguinAnimations.SWIMMING.bake(root);
        this.slidingAnimation = BabyPenguinAnimations.SLIDING.bake(root);
    }

    public static LayerDefinition getTexturedModelData()
    {
        final float yOffset = 17.5F;

        // Root
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartDefinition = modelData.getRoot();
        PartDefinition root = modelPartDefinition.addOrReplaceChild(PartNames.ROOT, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        // Body
        PartDefinition body = root.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -0.5F, -2.0F, 4.0F, 6.0F, 4.0F), PartPose.offset(0.0F, yOffset, 0.0F));

        // Head
        body.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create()
                        .texOffs(0, 10).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 3.0F, 4.0F)
                        .texOffs(16, 14).addBox(-1.0F, 0.0F, -3.0F, 2.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, -1.5F, 0.0F)
        );

        // Wings
        body.addOrReplaceChild(PartNames.LEFT_WING, CubeListBuilder.create().texOffs(16, 0).addBox(0.0F, 0.0F, -1.0F, 1.0F, 4.0F, 2.0F), PartPose.offset(2.0F, 0.0F, 0.0F));

        body.addOrReplaceChild(PartNames.RIGHT_WING, CubeListBuilder.create().texOffs(16, 6).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 4.0F, 2.0F), PartPose.offset(-2.0F, 0.0F, 0.0F));

        // Left Foot, Leg
        PartDefinition left_leg = body.addOrReplaceChild(PartNames.LEFT_LEG, CubeListBuilder.create().texOffs(16, 16).addBox(-0.5F, 2.0F, -1.0F, 1.0F, 0.0F, 1.0F), PartPose.offset(1.0F, 4.4F, 0.0F));
        left_leg.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 17).addBox(-1.0F, 0.0F, -2.0F, 1.0F, 0.0F, 1.0F), PartPose.offsetAndRotation(0.5F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

        // Right Foot, Leg
        PartDefinition right_leg = body.addOrReplaceChild(PartNames.RIGHT_LEG, CubeListBuilder.create().texOffs(4, 17).addBox(-0.5F, 2.0F, -1.0F, 1.0F, 0.0F, 1.0F), PartPose.offset(-1.0F, 4.4F, 0.0F));
        right_leg.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(8, 17).addBox(0.0F, 0.0F, -2.0F, 1.0F, 0.0F, 1.0F), PartPose.offsetAndRotation(-0.5F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

        // Tail
        body.addOrReplaceChild(PartNames.TAIL, CubeListBuilder.create().texOffs(16, 12).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 0.0F, 2.0F), PartPose.offset(0.0F, 5.5F, 2.0F));

        return LayerDefinition.create(modelData, 32, 32);
    }

    @Override
    public void setupAnim(PenguinRenderState state)
    {
        super.setupAnim(state);

        if (state.swimmingAnimationState.isStarted())
        {
            this.swimmingAnimation.apply(state.swimmingAnimationState, state.ageInTicks);
        }
        else if (state.walkingAnimationState.isStarted())
        {
            float limbSwingAmplitude = state.walkAnimationSpeed * 1.5f; // changes based on mob's velocity
            float limbSwingAnimationProgress = state.walkAnimationPos;

            // adjustable parameters for walk animation, can be used to make the limbs swing faster/slower and with more/less intensity
            float limbSwingSpeed = 3.0f;
            float limbSwingAmount = 2.5f;

            this.walkingAnimation.applyWalk(limbSwingAnimationProgress, limbSwingAmplitude, limbSwingSpeed, limbSwingAmount);
        }
        else if (state.idleAnimationState.isStarted())
        {
            this.idleAnimation.apply(state.idleAnimationState, state.ageInTicks);
        }
        else if (state.swimIdleAnimationState.isStarted())
        {
            this.swimIdleAnimation.apply(state.swimIdleAnimationState, state.ageInTicks);
        }
        else if (state.slidingAnimationState.isStarted())
        {
            this.slidingAnimation.apply(state.slidingAnimationState, state.ageInTicks);
        }
    }
}
