package com.victor.ambient_creatures.world.entity.client.penguin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.victor.ambient_creatures.AmbientCreatures;
import com.victor.ambient_creatures.world.entity.ModEntityModelLayers;
import com.victor.ambient_creatures.world.entity.client.penguin.adult.AdultPenguinModel;
import com.victor.ambient_creatures.world.entity.client.penguin.baby.BabyPenguinModel;
import com.victor.ambient_creatures.world.entity.animal.Penguin;
import net.minecraft.client.model.AdultAndBabyModelPair;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.HoldingEntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;

public class PenguinRenderer extends MobRenderer<Penguin, PenguinRenderState, PenguinModel>
{
    private final AdultAndBabyModelPair<PenguinModel> models;
    private static final Identifier ADULT_TEXTURE_PATH = Identifier.fromNamespaceAndPath(AmbientCreatures.MOD_ID, "textures/entity/penguin/adult_penguin.png");
    private static final Identifier BABY_TEXTURE_PATH = Identifier.fromNamespaceAndPath(AmbientCreatures.MOD_ID, "textures/entity/penguin/baby_penguin.png");
    private static final float shadowSize = 0.3F;

    public PenguinRenderer(EntityRendererProvider.Context context)
    {
        super(context, new AdultPenguinModel(context.bakeLayer(ModEntityModelLayers.ADULT_PENGUIN)), shadowSize);
        this.models = bakeModels(context);

        this.addLayer(new PenguinHeldItemLayer(this));
    }

    private static AdultAndBabyModelPair<PenguinModel> bakeModels(final EntityRendererProvider.Context context)
    {
        return new AdultAndBabyModelPair<>(
                new AdultPenguinModel(context.bakeLayer(ModEntityModelLayers.ADULT_PENGUIN)),
                new BabyPenguinModel(context.bakeLayer(ModEntityModelLayers.BABY_PENGUIN))
        );
    }

    @Override
    public PenguinRenderState createRenderState()
    {
        return new PenguinRenderState();
    }

    @Override
    public Identifier getTextureLocation(PenguinRenderState state) { return state.isBaby? BABY_TEXTURE_PATH : ADULT_TEXTURE_PATH; }

    @Override
    public void submit(final PenguinRenderState state, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final CameraRenderState camera)
    {
        this.model = this.models.getModel(state.isBaby);

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
