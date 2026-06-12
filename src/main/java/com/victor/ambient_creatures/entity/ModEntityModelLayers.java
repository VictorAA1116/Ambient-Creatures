package com.victor.ambient_creatures.entity;

import com.victor.ambient_creatures.AmbientCreatures;
import com.victor.ambient_creatures.entity.client.capybara.CapybaraModel;
import com.victor.ambient_creatures.entity.client.penguin.adult.AdultPenguinModel;
import com.victor.ambient_creatures.entity.client.penguin.baby.BabyPenguinModel;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.Identifier;

public class ModEntityModelLayers
{
    public static final ModelLayerLocation PENGUIN_ADULT = createMain("adult_penguin");
    public static final ModelLayerLocation PENGUIN_BABY = createMain("baby_penguin");
    public static final ModelLayerLocation CAPYBARA =  createMain("capybara");

    private static ModelLayerLocation createMain(String name)
    {
        return new ModelLayerLocation(Identifier.fromNamespaceAndPath(AmbientCreatures.MOD_ID, name), "main");
    }

    public static void registerModelLayers()
    {
        ModelLayerRegistry.registerModelLayer(PENGUIN_ADULT, AdultPenguinModel::getTexturedModelData);
        ModelLayerRegistry.registerModelLayer(PENGUIN_BABY, BabyPenguinModel::getTexturedModelData);

        ModelLayerRegistry.registerModelLayer(CAPYBARA, CapybaraModel::getTexturedModelData);
    }
}
