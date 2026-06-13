package com.victor.ambient_creatures.entity.client.capybara.adult;

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

public class AdultCapybaraModel extends CapybaraModel
{
    private final KeyframeAnimation idleAnimation;
    private final KeyframeAnimation walkingAnimation;
    private final KeyframeAnimation sittingAnimation;

    public AdultCapybaraModel(ModelPart root)
    {
        super(root);

        this.idleAnimation = AdultCapybaraAnimations.IDLE.bake(root);
        this.walkingAnimation = AdultCapybaraAnimations.WALKING.bake(root);
        this.sittingAnimation = AdultCapybaraAnimations.SITTING.bake(root);
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
