package com.victor.ambient_creatures.entity.client.penguin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.victor.ambient_creatures.AmbientCreatures;
import com.victor.ambient_creatures.entity.ModEntityModelLayers;
import com.victor.ambient_creatures.entity.custom.Penguin;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.HoldingEntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;

public class PenguinRenderer extends MobRenderer<Penguin, PenguinRenderState, PenguinModel>
{
    private static final Identifier TEXTURE_PATH = Identifier.fromNamespaceAndPath(AmbientCreatures.MOD_ID, "textures/entity/penguin/penguin.png");
    private static final float shadowSize = 0.3F;

    public PenguinRenderer(EntityRendererProvider.Context context)
    {
        super(context, new PenguinModel(context.bakeLayer(ModEntityModelLayers.PENGUIN)), shadowSize);

        this.addLayer(new PenguinHeldItemLayer(this));
    }

    @Override
    public PenguinRenderState createRenderState()
    {
        return new PenguinRenderState();
    }

    @Override
    public Identifier getTextureLocation(PenguinRenderState state)
    {
        return TEXTURE_PATH;
    }

    @Override
    public void submit(final PenguinRenderState state, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final CameraRenderState camera)
    {
        if (state.isBaby)
        {
            poseStack.scale(0.5F, 0.5F, 0.5F);
        }
        else
        {
            poseStack.scale(1.0F, 1.0F, 1.0F);
        }

        super.submit(state, poseStack, submitNodeCollector, camera);
    }

    @Override
    public void extractRenderState(Penguin penguin, PenguinRenderState state, float f)
    {
        super.extractRenderState(penguin, state, f);

        HoldingEntityRenderState.extractHoldingEntityRenderState(penguin, state, this.itemModelResolver);

        state.idleAnimationState.copyFrom(penguin.idleAnimationState);
        state.walkingAnimationState.copyFrom(penguin.walkingAnimationState);
        state.swimIdleAnimationState.copyFrom(penguin.swimIdleAnimationState);
        state.swimmingAnimationState.copyFrom(penguin.swimmingAnimationState);
        state.slidingAnimationState.copyFrom(penguin.slidingAnimationState);

        state.touchingWater = penguin.isInWater();
    }
}
