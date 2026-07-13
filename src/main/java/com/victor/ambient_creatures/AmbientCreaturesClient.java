package com.victor.ambient_creatures;

import com.victor.ambient_creatures.world.entity.ModEntities;
import com.victor.ambient_creatures.world.entity.ModEntityModelLayers;
import net.fabricmc.api.ClientModInitializer;

public class AmbientCreaturesClient implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        ModEntities.registerEntityRenderers();
    }
}
