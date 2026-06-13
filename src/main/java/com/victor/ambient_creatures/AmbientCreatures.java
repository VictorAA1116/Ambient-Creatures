package com.victor.ambient_creatures;

import com.victor.ambient_creatures.creativemodetab.ModCreativeModeTabs;
import com.victor.ambient_creatures.world.entity.ModEntities;
import com.victor.ambient_creatures.item.ModItems;
import com.victor.ambient_creatures.sound.ModSounds;
import com.victor.ambient_creatures.world.gen.ModWorldGeneration;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AmbientCreatures implements ModInitializer
{
	public static final String MOD_ID = "ambient_creatures";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize()
	{
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Initializing AmbientCreatures");

		ModEntities.registerModEntities();
		ModItems.registerModItems();
		ModCreativeModeTabs.registerCreativeModeTabs();
		ModSounds.registerSounds();
		ModWorldGeneration.generateModWorldGen();
	}
}