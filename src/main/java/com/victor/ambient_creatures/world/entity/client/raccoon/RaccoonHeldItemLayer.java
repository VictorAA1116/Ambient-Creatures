package com.victor.ambient_creatures.world.entity.client.raccoon;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;

public class RaccoonHeldItemLayer extends RenderLayer<RaccoonRenderState, RaccoonModel>
{

    public RaccoonHeldItemLayer(RenderLayerParent<RaccoonRenderState, RaccoonModel> renderer) { super(renderer); }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, RaccoonRenderState state, float yRot, float xRot)
    {
        ItemStackRenderState itemRenderState = state.heldItem;

        if (!itemRenderState.isEmpty())
        {
            poseStack.pushPose();

            RaccoonModel model = this.getParentModel();
            ModelPart root = model.root();
            ModelPart head = model.head;
            ModelPart body = model.body;
            ModelPart nose = model.nose;

            root.translateAndRotate(poseStack);

            // Apply body transform to capture all body animations (waddle, swim tilt, position, roll).
            body.translateAndRotate(poseStack);

            // Apply head transform to capture head animations (pitch adjustment for laying down, yaw/pitch from look).
            head.translateAndRotate(poseStack);

            // Apply nose transform to position the item at the mouth in world space.
            nose.translateAndRotate(poseStack);

            // Small offset to position item at the beak in world space
            if (state.isBaby)
            {
                poseStack.translate(0.0F, 0.05F, -0.1F);
            }
            else
            {
                poseStack.translate(0.0F, 0.05F, -0.2F);
            }

            // Rotate to orient the item correctly
            poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));

            itemRenderState.submit(poseStack, submitNodeCollector, lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);
            poseStack.popPose();
        }
    }
}
