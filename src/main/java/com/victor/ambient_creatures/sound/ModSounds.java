package com.victor.ambient_creatures.sound;

import com.victor.ambient_creatures.AmbientCreatures;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

public class ModSounds
{
    public static final SoundEvent PENGUIN_AMBIENT = registerSoundEvent("penguin_ambient");

    private static SoundEvent registerSoundEvent(String name)
    {
        Identifier id = Identifier.fromNamespaceAndPath(AmbientCreatures.MOD_ID, name);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
    }

    public static void registerSounds() { AmbientCreatures.LOGGER.info("Registering Mod Sounds for " + AmbientCreatures.MOD_ID); }
}
