package net.croc.doomedextras;

import net.mattlives.doomedmatu.registry.DoomedItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class RegistryCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DoomedExtrasMod.CREATIVE_TABS;

    public static final RegistryObject<CreativeModeTab> DOOMEDEXTRAS_TAB = CREATIVE_TABS.register("doomedextras_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.doomedextras"))
                    .icon(() -> new ItemStack(RegistryItems.GEIGER_COUNTER.get()))
                    .displayItems((parameters, output) -> {
                        output.accept(RegistryItems.AMMONIA_CRYSTAL.get());
                        output.accept(RegistryItems.GEIGER_COUNTER.get());
                        output.accept(RegistryItems.KEVLAR.get());

                        output.accept(RegistryItems.KIT_AI2.get());
                        output.accept(RegistryItems.KIT_IFAK.get());
                        output.accept(RegistryItems.KIT_ITK.get());
                        output.accept(RegistryItems.KIT_URGENCY_72H.get());

                        output.accept(RegistryItems.DOPANT_MIX.get());

                        output.accept(RegistryItems.COFFEE_TABLET.get());
                        output.accept(RegistryItems.CAFFEINE_SYRINGE.get());
                        output.accept(RegistryItems.GROUND_COFFEE.get());

                        output.accept(RegistryItems.LIDOCAINE_TABLET.get());
                        output.accept(RegistryItems.LIDOCAINE_SYRINGE.get());
                        output.accept(RegistryItems.LIDOCAINE_PHARMA.get());

                        output.accept(RegistryItems.IBUPROFEN.get());
                        output.accept(RegistryItems.HAND_SANITIZER.get());

                        output.accept(RegistryItems.POTASSIUM_IODIDE.get());

                        output.accept(RegistryItems.SOLAR_CELL.get());
                        output.accept(RegistryItems.SOLAR_PANEL.get());
                    })

                    .build());

    public static void register() {}
}