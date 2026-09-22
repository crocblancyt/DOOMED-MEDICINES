package net.croc.doomedextras.items;

import net.mattlives.doomedmatu.block.entity.RadioactiveWasteBarrelBlockEntity;
import net.mattlives.doomedmatu.client.RadiationProximity;
import net.mattlives.doomedmatu.registry.DoomedBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.Vec3;
import java.util.Map;

public class GeigerCounter extends Item {
    public GeigerCounter(Properties properties) {
        super(properties);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (level.isClientSide) {
            return InteractionResultHolder.success(stack);
        } else if (player instanceof ServerPlayer) {
            ServerPlayer sp = (ServerPlayer)player;
            sp.displayClientMessage(Component.literal(Math.floor(RadiationPrevention.intensity() * 10000) * 0.1F + " mSv"), true);
            return InteractionResultHolder.success(stack);
        } else {
            return InteractionResultHolder.pass(stack);
        }
    }

    public static class RadiationPrevention {
        private static final double DETECT_RANGE = 48.0F;
        private static final int CHUNK_R = 5;
        private static final long SCAN_PERIOD = 8L;
        private static final float CRYSTAL_WEIGHT = 1.25F;
        private static float cached;
        private static long lastScanTick = Long.MIN_VALUE;

        public static int level() {
            float i = intensity();
            if (i <= 0.0F) {
                return 0;
            } else {
                float t = (float)Math.sqrt(i);
                return Mth.clamp((int)Math.ceil((t * 5.0F)), 1, 5);
            }
        }

        public static float intensity() {
            Minecraft mc = Minecraft.getInstance();
            Player player = mc.player;
            ClientLevel level = mc.level;
            if (player != null && level != null) {
                long now = level.getGameTime();
                if (lastScanTick != Long.MIN_VALUE && now >= lastScanTick && now - lastScanTick < SCAN_PERIOD) {
                    return cached;
                } else {
                    lastScanTick = now;
                    cached = scan(level, player);
                    return cached;
                }
            } else {
                cached = 0.0F;
                return 0.0F;
            }
        }

        private static float scan(ClientLevel level, Player player) {
            Vec3 eye = player.getEyePosition();
            int pcx = player.chunkPosition().x;
            int pcz = player.chunkPosition().z;
            float best = 0.0F;

            for(int cx = pcx - CHUNK_R; cx <= pcx + CHUNK_R; ++cx) {
                for(int cz = pcz - CHUNK_R; cz <= pcz + CHUNK_R; ++cz) {
                    if (level.hasChunk(cx, cz)) {
                        LevelChunk chunk = level.getChunk(cx, cz);

                        for(Map.Entry<BlockPos, BlockEntity> e : chunk.getBlockEntities().entrySet()) {
                            BlockEntity be = e.getValue();
                            if (be instanceof RadioactiveWasteBarrelBlockEntity) {
                                BlockPos p = e.getKey();
                                double distSq = eye.distanceToSqr((double)p.getX() + (double)0.5F, (double)p.getY() + (double)0.5F, (double)p.getZ() + (double)0.5F);
                                if (!(distSq >= DETECT_RANGE*DETECT_RANGE)) {
                                    double dist = Math.sqrt(distSq);
                                    float t = (float)((double)1.0F - dist / DETECT_RANGE);
                                    boolean crystal = be.getBlockState().is(DoomedBlocks.CRYSTAL_IRRADIATED.get());
                                    float v = t * t * (crystal ? CRYSTAL_WEIGHT : 1.0F);
                                    if (v > best) {
                                        best = v;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            return Mth.clamp(best, 0.0F, 1.0F);
        }
    }
}