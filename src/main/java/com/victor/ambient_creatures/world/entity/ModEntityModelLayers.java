package com.victor.ambient_creatures.world.entity;

import com.victor.ambient_creatures.AmbientCreatures;
import com.victor.ambient_creatures.world.entity.client.capybara.adult.AdultCapybaraModel;
import com.victor.ambient_creatures.world.entity.client.capybara.baby.BabyCapybaraModel;
import com.victor.ambient_creatures.world.entity.client.penguin.adult.AdultPenguinModel;
import com.victor.ambient_creatures.world.entity.client.penguin.baby.BabyPenguinModel;
import com.victor.ambient_creatures.world.entity.client.raccoon.adult.AdultRaccoonModel;
import com.victor.ambient_creatures.world.entity.client.raccoon.baby.BabyRaccoonModel;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.Identifier;

public class ModEntityModelLayers
{
    // Capybara
    public static final ModelLayerLocation ADULT_CAPYBARA =  createMain("adult_capybara");
    public static final ModelLayerLocation BABY_CAPYBARA =  createMain("baby_capybara");

    // Penguin
    public static final ModelLayerLocation ADULT_PENGUIN = createMain("adult_penguin");
    public static final ModelLayerLocation BABY_PENGUIN = createMain("baby_penguin");

    // Raccoon
    public static final ModelLayerLocation ADULT_RACCOON = createMain("adult_raccoon");
    public static final ModelLayerLocation BABY_RACCOON = createMain("baby_raccoon");

    private static ModelLayerLocation createMain(String name)
    {
        return new ModelLayerLocation(Identifier.fromNamespaceAndPath(AmbientCreatures.MOD_ID, name), "main");
    }

    public static void registerModelLayers()
    {
        // Capybara
        ModelLayerRegistry.registerModelLayer(ADULT_CAPYBARA, AdultCapybaraModel::getTexturedModelData);
        ModelLayerRegistry.registerModelLayer(BABY_CAPYBARA, BabyCapybaraModel::getTexturedModelData);

        // Penguin
        ModelLayerRegistry.registerModelLayer(ADULT_PENGUIN, AdultPenguinModel::getTexturedModelData);
        ModelLayerRegistry.registerModelLayer(BABY_PENGUIN, BabyPenguinModel::getTexturedModelData);

        // Raccoon
        ModelLayerRegistry.registerModelLayer(ADULT_RACCOON, AdultRaccoonModel::getTexturedModelData);
        ModelLayerRegistry.registerModelLayer(BABY_RACCOON, BabyRaccoonModel::getTexturedModelData);
    }
}
