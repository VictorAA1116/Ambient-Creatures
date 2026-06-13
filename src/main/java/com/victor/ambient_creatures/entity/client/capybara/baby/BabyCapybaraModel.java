package com.victor.ambient_creatures.entity.client.capybara.baby;

import com.victor.ambient_creatures.entity.client.capybara.CapybaraModel;
import com.victor.ambient_creatures.entity.client.capybara.CapybaraRenderState;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class BabyCapybaraModel extends CapybaraModel
{
    private final KeyframeAnimation idleAnimation;
    private final KeyframeAnimation walkingAnimation;
    private final KeyframeAnimation sittingAnimation;

    public BabyCapybaraModel(ModelPart root)
    {
        super(root);

        this.idleAnimation = BabyCapybaraAnimations.IDLE.bake(root);
        this.walkingAnimation = BabyCapybaraAnimations.WALKING.bake(root);
        this.sittingAnimation = BabyCapybaraAnimations.SITTING.bake(root);
    }


    public static LayerDefinition getTexturedModelData()
    {
        final float yOffset = 17.5F;
        // Root
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartDefinition = modelData.getRoot();
        PartDefinition root = modelPartDefinition.addOrReplaceChild(PartNames.ROOT, CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition body = root.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -2.0F, -4.0F, 5.0F, 5.0F, 9.0F), PartPose.offset(0.0F, -6.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create().texOffs(0, 14).addBox(-2.0F, -2.9F, -0.3612F, 4.0F, 4.0F, 6.0F), PartPose.offsetAndRotation(0.0F, -2.0F, 4.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition left_ear = head.addOrReplaceChild(PartNames.LEFT_EAR, CubeListBuilder.create(), PartPose.offsetAndRotation(-1.5F, -2.7837F, -0.6876F, 0.0F, -0.5236F, 0.0F));
        left_ear.addOrReplaceChild("ear_r1", CubeListBuilder.create().texOffs(15, 16).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 2.0F, 0.0F), PartPose.offsetAndRotation(0.3F, 0.3F, 0.5F, 0.0F, 0.0F, -0.2618F));

        PartDefinition right_ear = head.addOrReplaceChild(PartNames.RIGHT_EAR, CubeListBuilder.create(), PartPose.offsetAndRotation(1.5F, -2.7837F, -0.6876F, 0.0F, 0.5236F, 0.0F));
        right_ear.addOrReplaceChild("ear_r2", CubeListBuilder.create().texOffs(15, 16).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 2.0F, 0.0F), PartPose.offsetAndRotation(-0.3F, 0.3F, 0.5F, 0.0F, 0.0F, 0.2618F));

        // Left Front Leg
        body.addOrReplaceChild(PartNames.LEFT_FRONT_LEG, CubeListBuilder.create().texOffs(2, 3).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 3.0F, 2.0F), PartPose.offset(-1.5F, 3.0F, 3.5F));

        // Right Front Leg
        body.addOrReplaceChild(PartNames.RIGHT_FRONT_LEG, CubeListBuilder.create().texOffs(2, 3).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 3.0F, 2.0F), PartPose.offset(1.5F, 3.0F, 3.5F));

        // Left Hind Leg
        body.addOrReplaceChild(PartNames.LEFT_HIND_LEG, CubeListBuilder.create().texOffs(20, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F), PartPose.offset(-1.5F, 3.0F, -3.5F));

        // Right Hind Leg
        body.addOrReplaceChild(PartNames.RIGHT_HIND_LEG, CubeListBuilder.create().texOffs(20, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F), PartPose.offset(1.5F, 3.0F, -3.5F));

        return LayerDefinition.create(modelData, 32, 32);
    }

    @Override
    public void setupAnim(CapybaraRenderState state)
    {
        super.setupAnim(state);

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
}
