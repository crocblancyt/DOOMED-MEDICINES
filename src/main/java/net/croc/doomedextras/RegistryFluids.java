package net.croc.doomedextras;

import net.croc.doomedextras.mixin.DoomedFluidsAccessor;
import net.mattlives.doomedmatu.body.DrugType;

public class RegistryFluids {
    static {
        DoomedFluidsAccessor.registerFluid("caffeine", 9863282, DrugType.valueOf("CAFFEINE"));
        DoomedFluidsAccessor.registerFluid("lidocaine", 6802296, DrugType.valueOf("LIDOCAINE"));
    }

    public static void register() {}
}