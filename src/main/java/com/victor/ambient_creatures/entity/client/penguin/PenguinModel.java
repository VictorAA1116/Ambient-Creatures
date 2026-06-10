package com.victor.ambient_creatures.entity.client.penguin;

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

    private final KeyframeAnimation idleAnimation;
    private final KeyframeAnimation walkingAnimation;
    private final KeyframeAnimation swimIdleAnimation;
    private final KeyframeAnimation swimmingAnimation;
    private final KeyframeAnimation slidingAnimation;

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

        this.idleAnimation = PenguinAnimations.IDLE.bake(root);
        this.walkingAnimation = PenguinAnimations.WALKING.bake(root);
        this.swimIdleAnimation = PenguinAnimations.SWIM_IDLE.bake(root);
        this.swimmingAnimation = PenguinAnimations.SWIMMING.bake(root);
        this.slidingAnimation = PenguinAnimations.SLIDING.bake(root);
    }

    public static LayerDefinition getTexturedModelData()
    {
        final float yOffset = 17.5F;

        // Root
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartDefinition = modelData.getRoot();
        PartDefinition root = modelPartDefinition.addOrReplaceChild(PartNames.ROOT, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        // Body
        PartDefinition body = root.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create().texOffs(0,0).addBox(-3.0F, -5.5F, -3.0F, 6.0F, 10.0F, 6.0F), PartPose.offset(0.0F, yOffset, 0.0F));

        // Head
        body.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create()
                .texOffs(0,18).addBox(-2.5F, -2.55F, -2.45F, 5.0F, 5.0F, 5.0F) // Main Head
                .texOffs(25, 0).addBox(-1.0F, 0.45F, -4.45F, 2.0F, 1.0F, 2.0F), // Beak
                PartPose.offsetAndRotation(0.0F, -7.95F, -0.05F, 0.0F, 0.0F, 0.0F));

        // Wings
        body.addOrReplaceChild(PartNames.LEFT_WING, CubeListBuilder.create().texOffs(23,18).addBox(0.0F, 0.0F, -1.5F, 1.0F, 7.0F, 3.0F), PartPose.offset(3.0F, -4.0F, 0.0F));

        body.addOrReplaceChild(PartNames.RIGHT_WING, CubeListBuilder.create().texOffs(23,18).mirror().addBox(-1.0F, 0.0F, -1.5F, 1.0F, 7.0F, 3.0F).mirror(false), PartPose.offset(-3.0F, -4.0F, 0.0F));

        // Foot, Leg
        PartDefinition leftLeg = body.addOrReplaceChild(PartNames.LEFT_LEG, CubeListBuilder.create().texOffs(24,7).addBox(-1.5F, 2.0F, -2.0F, 3.0F, 0.0F, 2.0F), PartPose.offset(1.5F, 4.45F, 0.0F));
        leftLeg.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(25,5).addBox(-1.0F, 0.0F, -2.0F, 1.0F, 0.0F, 2.0F), PartPose.offsetAndRotation(0.5F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

        // Foot, Leg
        PartDefinition rightLeg = body.addOrReplaceChild(PartNames.RIGHT_LEG, CubeListBuilder.create().texOffs(24,7).mirror().addBox(-1.5F, 2.0F, -2.0F, 3.0F, 0.0F, 2.0F).mirror(false), PartPose.offset(-1.5F, 4.45F, 0.0F));
        rightLeg.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(25,5).mirror().addBox(0.0F, 0.0F, -2.0F, 1.0F, 0.0F, 2.0F).mirror(false), PartPose.offsetAndRotation(-0.5F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

        // Tail
        body.addOrReplaceChild(PartNames.TAIL, CubeListBuilder.create().texOffs(22,11).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 0.0F, 4.0F), PartPose.offset(0.0F, 4.5F, 3.0F));

        return LayerDefinition.create(modelData, 64, 64);
    }

    @Override
    public void setupAnim(PenguinRenderState state)
    {
        super.setupAnim(state);
        this.resetPose();

        if (state.swimmingAnimationState.isStarted())
        {
            this.swimmingAnimation.apply(state.swimmingAnimationState, state.ageInTicks);
        }
        else if (state.walkingAnimationState.isStarted())
        {
            float limbSwingAmplitude = state.walkAnimationSpeed * 1.5f; // changes based on mob's velocity
            float limbSwingAnimationProgress = state.walkAnimationPos;

            // adjustable parameters for walk animation, can be used to make the limbs swing faster/slower and with more/less intensity
            float limbSwingSpeed = 2.0f;
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

        if (isLayingDown)
        {
            headPitch -= 90f;
        }

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
