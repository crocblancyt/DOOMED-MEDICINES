package net.croc.doomedextras.items;

import net.mattlives.doomedmatu.body.LimbMed;
import net.mattlives.doomedmatu.capability.DoomedDataUtil;
import net.mattlives.doomedmatu.item.LimbMedItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public final class HandSanitizer extends LimbMedItem {
    public HandSanitizer(Item.Properties properties) {
        super(properties, LimbMed.IODINE);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tip, TooltipFlag flag) {
        tip.add(Component.literal("Can be used to disinfect an injured limb.").withStyle(ChatFormatting.DARK_GRAY));
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (level.isClientSide) {
            return InteractionResultHolder.success(stack);
        } else if (player instanceof ServerPlayer) {
            ServerPlayer sp = (ServerPlayer)player;
            return DoomedDataUtil.get(sp).map((data) -> {
                if (data.isActive()) {
                    data.setDirtiness(0.0F);
                    // to fix: only on hands & hast to hurt (if wounded & on hands)

                    stack.hurtAndBreak(1, sp, (user) -> user.broadcastBreakEvent(hand));
                    level.playSound(null, sp.blockPosition(), SoundEvents.WET_GRASS_STEP, SoundSource.PLAYERS, 0.65F, 1.15F);
                    sp.displayClientMessage(Component.literal("Disinfected hands."), true);
                    DoomedDataUtil.sync(sp);
                    return InteractionResultHolder.success(stack);
                } else {
                    return InteractionResultHolder.fail(stack);
                }
            }).orElse(InteractionResultHolder.pass(stack));
        } else {
            return InteractionResultHolder.pass(stack);
        }
    }
}
