package com.victor.ambient_creatures.entity.client.penguin;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class PenguinEntityModel extends EntityModel<PenguinEntityRenderState>
{
    //private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart rightWing;
    private final ModelPart leftWing;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;
    private final ModelPart tail;

    protected PenguinEntityModel(ModelPart root)
    {
        super(root);

        //this.root = root.getChild(PartNames.ROOT);
        this.body = root.getChild(PartNames.BODY);
        this.head = root.getChild(PartNames.HEAD);
        this.rightWing = root.getChild(PartNames.RIGHT_WING);
        this.leftWing = root.getChild(PartNames.LEFT_WING);
        this.rightLeg = root.getChild(PartNames.RIGHT_LEG);
        this.leftLeg = root.getChild(PartNames.LEFT_LEG);
        this.tail = root.getChild(PartNames.TAIL);
    }

    public static LayerDefinition getTexturedModelData()
    {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition root = modelData.getRoot();

        final float yOffset = 17.5f;

        root.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create().texOffs(0,0).addBox(-3.0F, -5.5F, -3.0F, 6.0F, 10.0F, 6.0F), PartPose.offset(0.0F, 0.0F + yOffset, 0.0F));

        root.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create()
                .texOffs(0,18).addBox(-2.5F, -2.55F, -2.45F, 5.0F, 5.0F, 5.0F) // Main Head
                .texOffs(25, 0).addBox(-1.0F, 0.45F, -4.45F, 2.0F, 1.0F, 2.0F), // Beak
                PartPose.offsetAndRotation(0.0F, -7.95F + yOffset, -0.05F, 0.0F, 0.0F, 0.0F));

        root.addOrReplaceChild(PartNames.LEFT_WING, CubeListBuilder.create().texOffs(23,18).addBox(0.0F, 0.0F, -1.5F, 1.0F, 7.0F, 3.0F), PartPose.offset(3.0F, -4.0F + yOffset, 0.0F));

        root.addOrReplaceChild(PartNames.RIGHT_WING, CubeListBuilder.create().texOffs(23,18).mirror().addBox(-1.0F, 0.0F, -1.5F, 1.0F, 7.0F, 3.0F).mirror(false), PartPose.offset(-3.0F, -4.0F + yOffset, 0.0F));

        // Foot, Leg
        root.addOrReplaceChild(PartNames.LEFT_LEG, CubeListBuilder.create().texOffs(24,7).addBox(-1.5F, 2.0F, -2.0F, 3.0F, 0.0F, 2.0F), PartPose.offset(1.5F, 4.45F + yOffset, 0.0F));
        root.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(25,5).addBox(-1.0F, 0.0F, -2.0F, 1.0F, 0.0F, 2.0F), PartPose.offsetAndRotation(2F, 4.5F + yOffset, 0.0F, 1.5708F, 0.0F, 0.0F));

        // Foot, Leg
        root.addOrReplaceChild(PartNames.RIGHT_LEG, CubeListBuilder.create().texOffs(24,7).mirror().addBox(-1.5F, 2.0F, -2.0F, 3.0F, 0.0F, 2.0F).mirror(false), PartPose.offset(-1.5F, 4.45F + yOffset, 0.0F));
        root.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(25,5).mirror().addBox(0.0F, 0.0F, -2.0F, 1.0F, 0.0F, 2.0F).mirror(false), PartPose.offsetAndRotation(-2F, 4.5F + yOffset, 0.0F, 1.5708F, 0.0F, 0.0F));

        root.addOrReplaceChild(PartNames.TAIL, CubeListBuilder.create().texOffs(22,11).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 0.0F, 4.0F), PartPose.offset(0.0F, 4.5F + yOffset, 3.0F));

        return LayerDefinition.create(modelData, 64, 64);
    }

    @Override
    public void setupAnim(PenguinEntityRenderState state)
    {
        super.setupAnim(state);

        this.head.xRot = state.xRot * Mth.RAD_TO_DEG;
        this.head.yRot = state.yRot * Mth.RAD_TO_DEG;

        float limbSwingAmplitude = state.walkAnimationSpeed; // changes based on mob's velocity
        float limbSwingAnimationProgress = state.walkAnimationPos;

        // adjustable parameters for walk animation, can be used to make the limbs swing faster/slower and with more/less intensity
        float limbSwingSpeed = 0.2f;
        float limbSwingAmount = 1.4f;

        // Mth.Pi translates the cos wave one half phase over to make the limbs swing opposite of each other
        this.leftLeg.xRot = Mth.cos(limbSwingAnimationProgress * limbSwingSpeed + Mth.PI) * limbSwingAmount * limbSwingAmplitude;
        this.leftLeg.xRot = Mth.cos(limbSwingAnimationProgress * limbSwingSpeed) * limbSwingAmount * limbSwingAmplitude;
    }

//    @Override
//    public void setAngles(PenguinRenderState state) {
//        super.setAngles(state);
//        this. resetTransforms();
//
//        if (state.swimAnimationState.isRunning()) {
//            this.swimAnimation.apply(state.swimAnimationState, state.age, 1f);
//        } else if (state.walkAnimationState.isRunning()) {
//            this.walkAnimation.applyWalking(state.limbSwingAnimationProgress, state.limbSwingAmplitude * 1.5f, 2f, 2.5f);
//        } else if (state.idleAnimationState.isRunning()) {
//            this.idleAnimation.apply(state.idleAnimationState, state.age, 1f);
//        } else if (state.swimIdleAnimationState.isRunning()) {
//            this.swimIdleAnimation.apply(state.swimIdleAnimationState, state.age, 1f);
//        } else if (state.slideAnimationState.isRunning()) {
//            this.slideAnimation.apply(state.slideAnimationState, state.age, 1f);
//        }
//
//        if (state.touchingWater) {
//            this.body.pitch += state.pitch * ((float)Math.PI / 180F);
//        }
//
//        this.setHeadAngles(state.relativeHeadYaw, state.pitch, state.slideAnimationState.isRunning() || state.swimAnimationState.isRunning() || state.swimIdleAnimationState.isRunning());
//    }
//
//    private void setHeadAngles(float headYaw, float headPitch, boolean isSliding) {
//        headYaw = MathHelper.clamp(headYaw, -30.0f, 30.0f);
//        headPitch = MathHelper.clamp(headPitch, -25.0f, 45.0f);
//
//        if (isSliding) {
//            headPitch -= 90f;
//        }
//
//        this.head.pitch = headPitch * (float) (Math.PI / 180.0);
//
//        if (isSliding) {
//            this.head.roll = -(headYaw * 0.017453292F);
//        } else {
//            this.head.yaw = headYaw * 0.017453292F;
//        }
//    }
}
