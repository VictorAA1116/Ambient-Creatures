package com.victor.ambient_creatures.entity.client.capybara;

import com.mojang.blaze3d.vertex.PoseStack;
import com.victor.ambient_creatures.AmbientCreatures;
import com.victor.ambient_creatures.entity.ModEntityModelLayers;
import com.victor.ambient_creatures.entity.custom.Capybara;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import org.joml.Vector3f;

public class CapybaraRenderer extends MobRenderer<Capybara, CapybaraRenderState, CapybaraModel>
{
    private static final Identifier TEXTURE_PATH = Identifier.fromNamespaceAndPath(AmbientCreatures.MOD_ID, "textures/entity/capybara/capybara.png");
    private static final float shadowSize = 0.5F;

    public CapybaraRenderer(EntityRendererProvider.Context context)
    {
        super(context, new CapybaraModel(context.bakeLayer(ModEntityModelLayers.CAPYBARA)), shadowSize);
    }

    @Override
    public CapybaraRenderState createRenderState() { return new CapybaraRenderState(); }

    @Override
    public Identifier getTextureLocation(CapybaraRenderState state) { return TEXTURE_PATH; }

    @Override
    public void submit(final CapybaraRenderState state, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final CameraRenderState camera)
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
    public void extractRenderState(Capybara capybara, CapybaraRenderState state, float f)
    {
        super.extractRenderState(capybara, state, f);

        state.idleAnimationState.copyFrom(capybara.idleAnimationState);
        state.walkingAnimationState.copyFrom(capybara.walkingAnimationState);
        state.sittingAnimationState.copyFrom(capybara.sittingAnimationState);
    }
}
