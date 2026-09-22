package net.croc.doomedextras.items;

import net.croc.doomedextras.RegistrySounds;
import net.mattlives.doomedmatu.capability.DoomedData;
import net.mattlives.doomedmatu.capability.DoomedDataUtil;
import net.mattlives.doomedmatu.item.MedDrinkItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.function.BiConsumer;

public class ZippedMedkit extends MedDrinkItem {
    private final BiConsumer<ServerPlayer, DoomedData> effect;

    public ZippedMedkit(Properties properties, String message, BiConsumer<ServerPlayer, DoomedData> effect) {
        super(properties, message, effect);
        this.effect = effect;
    }

    @Override
    public SoundEvent getDrinkingSound() {
        return RegistrySounds.UNZIPPING_KIT.get();
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 24;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide && entity instanceof ServerPlayer sp) {
            DoomedDataUtil.get(sp).ifPresent((d) -> {
                if (d.isActive()) {
                    this.effect.accept(sp, d);
                    stack.shrink(1);
                    DoomedDataUtil.sync(sp);
                }
            });
        }

        return stack;
    }
}