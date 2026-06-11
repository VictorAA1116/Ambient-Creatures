package com.victor.ambient_creatures.entity;

import com.victor.ambient_creatures.AmbientCreatures;
import com.victor.ambient_creatures.entity.client.capybara.CapybaraModel;
import com.victor.ambient_creatures.entity.client.penguin.PenguinModel;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.Identifier;

public class ModEntityModelLayers
{
    public static final ModelLayerLocation PENGUIN = createMain("penguin");
    public static final ModelLayerLocation CAPYBARA =  createMain("capybara");

    private static ModelLayerLocation createMain(String name)
    {
        return new ModelLayerLocation(Identifier.fromNamespaceAndPath(AmbientCreatures.MOD_ID, name), "main");
    }

    public static void registerModelLayers()
    {
        ModelLayerRegistry.registerModelLayer(PENGUIN, PenguinModel::getTexturedModelData);
        ModelLayerRegistry.registerModelLayer(CAPYBARA, CapybaraModel::getTexturedModelData);
    }
}
