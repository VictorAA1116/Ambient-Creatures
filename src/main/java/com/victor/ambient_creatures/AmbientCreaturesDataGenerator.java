package com.victor.ambient_creatures;

import com.victor.ambient_creatures.datagen.ModEntityLootTableProvider;
import com.victor.ambient_creatures.datagen.ModItemTagProvider;
import com.victor.ambient_creatures.datagen.ModModelProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class AmbientCreaturesDataGenerator implements DataGeneratorEntrypoint
{
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator)
	{
		var pack = fabricDataGenerator.createPack();

		pack.addProvider(ModModelProvider::new);
		pack.addProvider(ModItemTagProvider::new);
		pack.addProvider(ModEntityLootTableProvider::new);
	}
}
