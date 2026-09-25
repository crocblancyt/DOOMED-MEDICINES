package net.croc.doomedextras;

import net.mattlives.doomedmatu.block.HeatSource;
import net.mattlives.doomedmatu.compat.jei.AcquisitionCatalog;
import net.mattlives.doomedmatu.compat.jei.AcquisitionCategory;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static net.croc.doomedextras.DoomedExtrasMod.MOD_ID;

@Mod(MOD_ID)
@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DoomedExtrasMod {
    public static final String MOD_ID = "doomedextras";

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, MOD_ID);

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, DoomedExtrasMod.MOD_ID);

    public DoomedExtrasMod() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        HeatSource
        RegistryItems.register();
        RegistryBlocks.register();
        RegistryCreativeTab.register();
        RegistryFluids.register();

        ITEMS.register(modBus);
        SOUND_EVENTS.register(modBus);
        CREATIVE_TABS.register(modBus);

        RegistryItems.register();
        RegistrySounds.register();
    }
}