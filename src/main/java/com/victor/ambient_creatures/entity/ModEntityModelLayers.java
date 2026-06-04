package com.victor.ambient_creatures.entity;

import com.victor.ambient_creatures.AmbientCreatures;
import com.victor.ambient_creatures.entity.client.penguin.PenguinEntityModel;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.Identifier;

public class ModEntityModelLayers
{
    public static final ModelLayerLocation PENGUIN = createMain("penguin");

    private static ModelLayerLocation createMain(String name)
    {
        return new ModelLayerLocation(Identifier.fromNamespaceAndPath(AmbientCreatures.MOD_ID, name), "main");
    }

    public static void registerModelLayers()
    {
        ModelLayerRegistry.registerModelLayer(PENGUIN, PenguinEntityModel::getTexturedModelData);
    }
}
