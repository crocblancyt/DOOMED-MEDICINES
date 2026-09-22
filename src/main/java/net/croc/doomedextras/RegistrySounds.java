package net.croc.doomedextras;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RegistrySounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DoomedExtrasMod.SOUND_EVENTS;

    public static final RegistryObject<SoundEvent> PILLS_BOTTLE = SOUND_EVENTS
            .register("pills_bottle",
                    () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomedExtrasMod.MOD_ID, "pills_bottle")));

    public static final RegistryObject<SoundEvent> PILLS_TABLET = SOUND_EVENTS
            .register("pills_tablet",
                    () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomedExtrasMod.MOD_ID, "pills_tablet")));

    public static final RegistryObject<SoundEvent> UNZIPPING_KIT = SOUND_EVENTS
            .register("unzipping_kit",
                    () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomedExtrasMod.MOD_ID, "unzipping_kit")));

    public static void register() {}
}