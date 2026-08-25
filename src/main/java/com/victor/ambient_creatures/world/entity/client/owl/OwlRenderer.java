package com.victor.ambient_creatures.world.entity.client.owl;

import com.mojang.blaze3d.vertex.PoseStack;
import com.victor.ambient_creatures.AmbientCreatures;
import com.victor.ambient_creatures.world.entity.ModEntityModelLayers;
import com.victor.ambient_creatures.world.entity.animal.Owl;
import com.victor.ambient_creatures.world.entity.client.owl.adult.AdultOwlModel;
import com.victor.ambient_creatures.world.entity.client.owl.baby.BabyOwlModel;
import net.minecraft.client.model.AdultAndBabyModelPair;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;

public class OwlRenderer extends MobRenderer<Owl, OwlRenderState, OwlModel>
{
    private final AdultAndBabyModelPair<OwlModel> models;

    private static final Identifier ADULT_TEXTURE_PATH = Identifier.fromNamespaceAndPath(AmbientCreatures.MOD_ID, "textures/entity/owl/barn/adult_owl_barn.png");
    private static final Identifier BABY_TEXTURE_PATH = Identifier.fromNamespaceAndPath(AmbientCreatures.MOD_ID, "textures/entity/owl/barn/baby_owl_barn.png");

    private static final float shadowSize = 0.5F;

    public OwlRenderer(EntityRendererProvider.Context context)
    {
        super(context, new AdultOwlModel(context.bakeLayer(ModEntityModelLayers.ADULT_OWL)), shadowSize);
        this.models = bakeModels(context);
    }

    private static AdultAndBabyModelPair<OwlModel> bakeModels(final EntityRendererProvider.Context context)
    {
        return new AdultAndBabyModelPair<>(
                new AdultOwlModel(context.bakeLayer(ModEntityModelLayers.ADULT_OWL)),
                new BabyOwlModel(context.bakeLayer(ModEntityModelLayers.BABY_OWL))
        );
    }

    @Override
    public Identifier getTextureLocation(OwlRenderState state) { return state.isBaby ? BABY_TEXTURE_PATH : ADULT_TEXTURE_PATH;}

    @Override
    public OwlRenderState createRenderState() { return new OwlRenderState(); }

    @Override
    public void submit(final OwlRenderState state, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final CameraRenderState camera)
    {
        this.model = this.models.getModel(state.isBaby);

        super.submit(state, poseStack, submitNodeCollector, camera);
    }

    @Override
    public void extractRenderState(Owl owl, OwlRenderState state, float f)
    {
        super.extractRenderState(owl, state, f);

        state.idleAnimationState.copyFrom(owl.idleAnimationState);
        state.walkingAnimationState.copyFrom(owl.walkingAnimationState);
        state.flyingAnimationState.copyFrom(owl.flyingAnimationState);
    }
}

