package com.victor.ambient_creatures.world.entity.client.capybara;

import com.mojang.blaze3d.vertex.PoseStack;
import com.victor.ambient_creatures.AmbientCreatures;
import com.victor.ambient_creatures.world.entity.ModEntityModelLayers;
import com.victor.ambient_creatures.world.entity.client.capybara.adult.AdultCapybaraModel;
import com.victor.ambient_creatures.world.entity.client.capybara.baby.BabyCapybaraModel;
import com.victor.ambient_creatures.world.entity.animal.Capybara;
import net.minecraft.client.model.AdultAndBabyModelPair;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;

public class CapybaraRenderer extends MobRenderer<Capybara, CapybaraRenderState, CapybaraModel>
{
    private final AdultAndBabyModelPair<CapybaraModel> models;
    private static final Identifier ADULT_TEXTURE_PATH = Identifier.fromNamespaceAndPath(AmbientCreatures.MOD_ID, "textures/entity/capybara/adult_capybara.png");
    private static final Identifier BABY_TEXTURE_PATH = Identifier.fromNamespaceAndPath(AmbientCreatures.MOD_ID, "textures/entity/capybara/baby_capybara.png");
    private static final float shadowSize = 0.5F;

    public CapybaraRenderer(EntityRendererProvider.Context context)
    {
        super(context, new AdultCapybaraModel(context.bakeLayer(ModEntityModelLayers.ADULT_CAPYBARA)), shadowSize);
        this.models = bakeModels(context);
    }

    private static AdultAndBabyModelPair<CapybaraModel> bakeModels(final EntityRendererProvider.Context context)
    {
        return new AdultAndBabyModelPair<>(
                new AdultCapybaraModel(context.bakeLayer(ModEntityModelLayers.ADULT_CAPYBARA)),
                new BabyCapybaraModel(context.bakeLayer(ModEntityModelLayers.BABY_CAPYBARA))
        );
    }

    @Override
    public CapybaraRenderState createRenderState() { return new CapybaraRenderState(); }

    @Override
    public Identifier getTextureLocation(CapybaraRenderState state)
    {
        return state.isBaby ? BABY_TEXTURE_PATH : ADULT_TEXTURE_PATH;
    }

    @Override
    public void submit(final CapybaraRenderState state, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final CameraRenderState camera)
    {
        this.model = this.models.getModel(state.isBaby);

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
