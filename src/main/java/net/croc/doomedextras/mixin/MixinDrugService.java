package net.croc.doomedextras.mixin;

import net.mattlives.doomedmatu.body.BodyPart;
import net.mattlives.doomedmatu.body.DrugService;
import net.mattlives.doomedmatu.body.DrugType;
import net.mattlives.doomedmatu.body.LimbData;
import net.mattlives.doomedmatu.capability.DoomedData;
import net.mattlives.doomedmatu.registry.DoomedFeatures;
import net.mattlives.doomedmatu.registry.DoomedSounds;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DrugService.class)
public class MixinDrugService {
    private static void playAt(ServerPlayer sp, SoundEvent ev, float vol, float pitch) {
        sp.level().playSound(null, sp.getX(), sp.getY(), sp.getZ(), ev, SoundSource.PLAYERS, vol, pitch);
    }

    private static float doseKeep(float fullDoseKeep, float activeMl, float referenceMl) {
        return (float)Math.pow(fullDoseKeep, (Math.max(0.0F, activeMl) / Math.max(0.001F, referenceMl)));
    }

    @Inject(method = "applyDrugEffect", at = @At("HEAD"), remap = false, cancellable = true)
    private static void mixinApplyDrugEffect(ServerPlayer sp, DoomedData data, BodyPart part, DrugType drug, float ml, CallbackInfo ci) {
        switch (drug.name()) {
            case "CAFFEINE" :
                ci.cancel();
                data.setAdrenaline(data.getAdrenaline() + 0.7F * ml);
                data.setConsciousness(Math.max(100, data.getConsciousness() + ml * 0.24F));
                data.setEnergy(data.getEnergy() - ml * 0.12F);
                data.setCaffeinated(data.getCaffeinated() + 600);
                data.setBpMedicineOffset(data.getBpMedicineOffset() + ml * 0.4F);
                data.setNapping(false);
            case "LIDOCAINE" :
                ci.cancel();
                LimbData l = data.getLimb(part);
                l.setPain(l.getPain() * ml * -1.0F);
                data.setFibrillation(Math.max(0, data.getFibrillation() - 0.55F * ml));
        }
    }

    @Inject(method = "playInjectionSound", at = @At("HEAD"), remap = false, cancellable = true)
    private static void mixinPlayInjectionSound(ServerPlayer sp, DrugType drug, CallbackInfo ci) {
        if (drug.name().equals("CAFFEINE")) {
            ci.cancel();
            playAt(sp, DoomedSounds.SYRINGE.get(), 0.8F, 1.2F);
            playAt(sp, DoomedSounds.HEARTTHUMP_HEAVY.get(), 0.7F, 1.3F);
        }
    }
}