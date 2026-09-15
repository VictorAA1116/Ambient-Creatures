package com.victor.ambient_creatures.world.entity.client.penguin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import org.joml.Matrix4fc;

public class PenguinHeldItemLayer extends RenderLayer<PenguinRenderState, PenguinModel>
{
    public PenguinHeldItemLayer(RenderLayerParent<PenguinRenderState, PenguinModel> renderer)
    {
        super(renderer);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, PenguinRenderState state, float yRot, float xRot)
    {
        ItemStackRenderState itemRenderState = state.heldItem;

        if (!itemRenderState.isEmpty())
        {
            boolean isSliding = state.slidingAnimationState.isStarted() || state.swimIdleAnimationState.isStarted() || state.swimmingAnimationState.isStarted();
            poseStack.pushPose();

            PenguinModel model = this.getParentModel();
            ModelPart root = model.root();
            ModelPart head = model.head;
            ModelPart body = model.body;

            root.translateAndRotate(poseStack);

            // Apply body transform to capture all body animations (waddle, swim tilt, position, roll).
            body.translateAndRotate(poseStack);

            // Apply head transform to capture head animations (pitch adjustment for laying down, yaw/pitch from look).
            head.translateAndRotate(poseStack);

            // Small offset to position item at the beak in world space
            if (state.isBaby)
            {
                if (isSliding)
                {
                    poseStack.translate(0.0F, -0.25F, -0.075F);
                }
                else
                {
                    poseStack.translate(0.0F, 0.075F, -0.25F);
                }
            }
            else if (isSliding)
            {
                poseStack.translate(0.0F, -0.37F, -0.1F);
            }
            else
            {
                poseStack.translate(0.0F, 0.1F, -0.37F);
            }

            // Rotate to orient the item correctly
            poseStack.rotateDegrees(Axis.XP, 90.0F);

            itemRenderState.submit(poseStack, submitNodeCollector, lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);
            poseStack.popPose();
        }
    }
}
