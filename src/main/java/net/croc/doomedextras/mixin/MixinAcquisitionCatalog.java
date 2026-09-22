package net.croc.doomedextras.mixin;

import net.croc.doomedextras.RegistryItems;
import net.mattlives.doomedmatu.compat.jei.AcquisitionCatalog;
import net.mattlives.doomedmatu.compat.jei.AcquisitionRecipe;
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

import java.util.Map;
import java.util.function.Supplier;

@Mixin(AcquisitionCatalog.class)
public abstract class MixinAcquisitionCatalog {
    @Unique
    private static final Source GENERAL_TRADER = source("general_trader", () -> new ItemStack(Items.EMERALD));

    @Unique
    private static record Source(String id, Supplier<ItemStack> icon) {
    }

    @Unique
    private static Source source(String id, Supplier<ItemStack> icon) {
        return new Source(id, icon);
    }

    @Unique
    private static Component sourceName(Source source) {
        return Component.translatable("jei.doomedmatu.acquisition.source." + source.id() + ".name");
    }

    @Unique
    private static Component sourceDescription(Source source) {
        return Component.translatable("jei.doomedmatu.acquisition.source." + source.id() + ".description");
    }

    @Unique
    private static void add(Map<String, AcquisitionRecipe> recipes, Source source, ItemStack result) {
        if (result != null && !result.isEmpty()) {
            ResourceLocation resultId = ForgeRegistries.ITEMS.getKey(result.getItem());
            if (resultId != null) {
                String key = source.id() + "|" + resultId;
                recipes.putIfAbsent(key, AcquisitionRecipe.item(source.id(), source.icon().get(), result, sourceName(source), sourceDescription(source)));
            }
        }
    }

    @Inject(method = "addTraders", at = @At("HEAD"), remap = false)
    private static void mixinAddTraders(Map<String, AcquisitionRecipe> recipes, CallbackInfo ci) {
        for (RegistryObject<Item> item : RegistryItems.loadTradables()) {
            add(recipes, GENERAL_TRADER, new ItemStack(item.get()));
        }
    }
}