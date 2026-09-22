package net.croc.doomedextras.client;

import net.croc.doomedextras.DoomedExtrasMod;
import net.mattlives.doomedmatu.client.ClientForgeEvents;
import net.mattlives.doomedmatu.item.ItemInfo;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber(modid = DoomedExtrasMod.MOD_ID, value = {Dist.CLIENT})
public final class DoomedExtrasClientEvents {
    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(event.getItemStack().getItem());
        if (id != null && id.getNamespace().equals(DoomedExtrasMod.MOD_ID)) {
            String key = "tooltip."+DoomedExtrasMod.MOD_ID+"." + id.getPath();
            if (I18n.exists(key)) {
                if (Screen.hasShiftDown()) {
                    for (String line : I18n.get(key, new Object[0]).split("\n")) {
                        event.getToolTip().add(Component.literal(line).withStyle(ChatFormatting.GRAY));
                    }
                } else {
                    event.getToolTip().add(Component.translatable("tooltip.doomedmatu.hold_shift").withStyle(new ChatFormatting[]{ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}));
                }
            }

            ItemStack stack = event.getItemStack();
            if (ItemInfo.isRegistered(stack)) {
                int value = ItemInfo.value(stack);
                if (value > 0) {
                    event.getToolTip().add(Component.literal("Value: " + value).withStyle(ChatFormatting.YELLOW));
                }
            }
        }
    }
}
