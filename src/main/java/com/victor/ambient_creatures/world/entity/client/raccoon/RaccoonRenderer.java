package com.victor.ambient_creatures.world.entity.client.raccoon;

import com.mojang.blaze3d.vertex.PoseStack;
import com.victor.ambient_creatures.AmbientCreatures;
import com.victor.ambient_creatures.world.entity.ModEntityModelLayers;
import com.victor.ambient_creatures.world.entity.animal.Raccoon;
import com.victor.ambient_creatures.world.entity.client.raccoon.adult.AdultRaccoonModel;
import com.victor.ambient_creatures.world.entity.client.raccoon.baby.BabyRaccoonModel;
import net.minecraft.client.model.AdultAndBabyModelPair;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;

public class RaccoonRenderer extends MobRenderer<Raccoon, RaccoonRenderState, RaccoonModel>
{
    private final AdultAndBabyModelPair<RaccoonModel> models;
    private static final Identifier ADULT_TEXTURE_PATH = Identifier.fromNamespaceAndPath(AmbientCreatures.MOD_ID, "textures/entity/raccoon/adult_raccoon.png");
    private static final Identifier BABY_TEXTURE_PATH = Identifier.fromNamespaceAndPath(AmbientCreatures.MOD_ID, "textures/entity/raccoon/baby_raccoon.png");
    private static final float shadowSize = 0.7F;
    public RaccoonRenderer(EntityRendererProvider.Context context)
    {
        super(context, new AdultRaccoonModel(context.bakeLayer(ModEntityModelLayers.ADULT_RACCOON)), shadowSize);
        this.models = bakeModels(context);
    }

    private static AdultAndBabyModelPair<RaccoonModel> bakeModels(final EntityRendererProvider.Context context)
    {
        return new AdultAndBabyModelPair<>(
                new AdultRaccoonModel(context.bakeLayer(ModEntityModelLayers.ADULT_RACCOON)),
                new BabyRaccoonModel(context.bakeLayer(ModEntityModelLayers.BABY_RACCOON))
        );
    }

    @Override
    public RaccoonRenderState createRenderState() { return new RaccoonRenderState(); }

    @Override
    public Identifier getTextureLocation(RaccoonRenderState state) { return state.isBaby? BABY_TEXTURE_PATH : ADULT_TEXTURE_PATH; }

    @Override
    public void submit(final RaccoonRenderState state, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final CameraRenderState camera)
    {
        this.model = this.models.getModel(state.isBaby);

        super.submit(state, poseStack, submitNodeCollector, camera);
    }

    @Override
    public void extractRenderState(Raccoon raccoon, RaccoonRenderState state, float f)
    {
        super.extractRenderState(raccoon, state, f);

        state.idleAnimationState.copyFrom(raccoon.idleAnimationState);
        state.walkingAnimationState.copyFrom(raccoon.walkingAnimationState);
        state.standingAnimationState.copyFrom(raccoon.standingAnimationState);
        state.sleepingAnimationState.copyFrom(raccoon.sleepingAnimationState);
    }
}
