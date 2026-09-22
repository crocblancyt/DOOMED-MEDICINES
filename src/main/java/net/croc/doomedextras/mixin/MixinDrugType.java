package net.croc.doomedextras.mixin;

import net.mattlives.doomedmatu.body.DrugType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.ArrayList;
import java.util.Arrays;

@Mixin(DrugType.class)
public abstract class MixinDrugType {
    @Shadow(remap = false)
    @Mutable
    private static DrugType[] $VALUES;

    @Invoker("<init>")
    public static DrugType invokeInit(String name, int ordinal) {
        throw new AssertionError();
    }

    @Unique
    private static DrugType addVariant(String name, int ordinal) {
        ArrayList<DrugType> variants = new ArrayList<>(Arrays.asList($VALUES));
        DrugType value = invokeInit(name, variants.get(variants.size() - 1).ordinal() + 1);
        variants.add(value);
        $VALUES = variants.toArray(new DrugType[0]);
        return value;
    }

    private static final DrugType CAFFEINE = addVariant("CAFFEINE", 0);
    private static final DrugType LIDOCAINE = addVariant("LIDOCAINE", 0);
}