package net.croc.doomedextras.mixin;

import net.mattlives.doomedmatu.body.DrugType;
import net.mattlives.doomedmatu.registry.DoomedFluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(DoomedFluids.class)
public interface DoomedFluidsAccessor {
    @Invoker("fluid")
    static void registerFluid(String name, int rgb, DrugType drug) {
        throw new AssertionError();
    }
}