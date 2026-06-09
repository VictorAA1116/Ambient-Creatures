package com.victor.ambient_creatures.entity.client.penguin;

import com.victor.ambient_creatures.AmbientCreatures;
import com.victor.ambient_creatures.entity.ModEntityModelLayers;
import com.victor.ambient_creatures.entity.custom.PenguinEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class PenguinEntityRenderer extends MobRenderer<PenguinEntity, PenguinEntityRenderState, PenguinEntityModel>
{
    private static final Identifier TEXTURE_PATH = Identifier.fromNamespaceAndPath(AmbientCreatures.MOD_ID, "textures/entity/penguin/penguin.png");
    private static final float shadowSize = 0.3F;

    public PenguinEntityRenderer(EntityRendererProvider.Context context)
    {
        super(context, new PenguinEntityModel(context.bakeLayer(ModEntityModelLayers.PENGUIN)), shadowSize);
    }

    @Override
    public PenguinEntityRenderState createRenderState()
    {
        return new PenguinEntityRenderState();
    }

    @Override
    public Identifier getTextureLocation(PenguinEntityRenderState state)
    {
        return TEXTURE_PATH;
    }

    @Override
    public void extractRenderState(PenguinEntity penguinEntity, PenguinEntityRenderState state, float f)
    {
        super.extractRenderState(penguinEntity, state, f);

        state.idleAnimationState.copyFrom(penguinEntity.idleAnimationState);
        state.walkingAnimationState.copyFrom(penguinEntity.walkingAnimationState);
        state.swimIdleAnimationState.copyFrom(penguinEntity.swimIdleAnimationState);
        state.swimmingAnimationState.copyFrom(penguinEntity.swimmingAnimationState);
        state.slidingAnimationState.copyFrom(penguinEntity.slidingAnimationState);

        state.touchingWater = penguinEntity.isInWater();
    }
}
