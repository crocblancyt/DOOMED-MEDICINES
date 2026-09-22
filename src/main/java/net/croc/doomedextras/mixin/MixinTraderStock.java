package net.croc.doomedextras.mixin;

import net.croc.doomedextras.RegistryItems;
import net.mattlives.doomedmatu.compat.jei.AcquisitionCatalog;
import net.mattlives.doomedmatu.compat.jei.AcquisitionRecipe;
import net.mattlives.doomedmatu.entity.TraderStock;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Mixin(TraderStock.class)
public class MixinTraderStock {
    @Unique
    private static final List<RegistryObject<Item>> TRADABLES = RegistryItems.loadTradables();

    @Inject(method = "pool", at = @At("RETURN"), remap = false, cancellable = true)
    private static void mixinPool(CallbackInfoReturnable<List<Item>> cir) {
        List<Item> pool = cir.getReturnValue();
        for (RegistryObject<Item> tradable : TRADABLES) {
            pool.add(tradable.get());
        }
        cir.setReturnValue(pool);
    }
}