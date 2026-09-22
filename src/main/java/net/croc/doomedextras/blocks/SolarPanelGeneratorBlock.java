package net.croc.doomedextras.blocks;

import net.mattlives.doomedmatu.block.HorizontalFacingEntityBlock;
import net.mattlives.doomedmatu.block.entity.FuelGeneratorBlockEntity;
import net.mattlives.doomedmatu.registry.DoomedBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

public class SolarPanelGeneratorBlock extends HorizontalFacingEntityBlock {
    public SolarPanelGeneratorBlock(BlockBehaviour.Properties props) {
        super(props);
        this.registerDefaultState(this.defaultBlockState().setValue(BlockStateProperties.LIT, false));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> b) {
        super.createBlockStateDefinition(b);
        b.add(BlockStateProperties.LIT);
    }

    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new FuelGeneratorBlockEntity(pos, state);
    }

    /*public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        //return level.isClientSide() ? null : createTickerHelper(type, (BlockEntityType)DoomedBlockEntities.FUEL_GENERATOR.get(), (lvl, pos, st, be) -> be.serverTick(lvl));
    }*/

    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide()) {
            BlockEntity var8 = level.getBlockEntity(pos);
            if (var8 instanceof FuelGeneratorBlockEntity) {
                FuelGeneratorBlockEntity be = (FuelGeneratorBlockEntity)var8;
                NetworkHooks.openScreen((ServerPlayer)player, be, pos);
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide());
    }
}
