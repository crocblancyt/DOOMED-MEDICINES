package net.croc.doomedextras;

import net.croc.doomedextras.blocks.SolarPanelGeneratorBlock;
import net.mattlives.doomedmatu.item.RecoverableBlockItemPolicy;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.BiFunction;

public class RegistryBlocks {
    /*
    public static DeferredRegister<Block> BLOCKS = DoomedExtrasMod.BLOCKS;
    public static DeferredRegister<Item> BLOCK_ITEMS = DoomedExtrasMod.ITEMS;

    private static RegistryObject<Item> recoverableItem(String id, RegistryObject<Block> block, BiFunction<Block, Item.Properties, Item> factory) {
        return BLOCK_ITEMS.register(id, () -> factory.apply(block.get(), RecoverableBlockItemPolicy.properties(id)));
    }

    public static final RegistryObject<Block> SOLAR_PANEL_GENERATOR = BLOCKS.register("solar_panel_generator", () -> new SolarPanelGeneratorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
            .strength(1.5F)
            .noOcclusion()));

    public static final RegistryObject<Item> SOLAR_PANEL_GENERATOR_ITEM = recoverableItem("solar_panel_generator", SOLAR_PANEL_GENERATOR, BlockItem::new);
*/
    public static void register() {}
}