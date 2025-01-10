package net.cestog.qolitems.Item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class WoodenBucketItem extends Item {
    private final Fluid containedFluid;

    public WoodenBucketItem(Properties properties, Fluid fluid) {
        super(properties);
        this.containedFluid = fluid;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack heldItem = player.getItemInHand(hand);
        BlockHitResult hitResult = getPlayerPOVHitResult(level, player,
                this.containedFluid == Fluids.EMPTY ? ClipContext.Fluid.SOURCE_ONLY : ClipContext.Fluid.NONE);

        if (hitResult.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(heldItem);
        }

        if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos pos = hitResult.getBlockPos();
            Direction direction = hitResult.getDirection();
            BlockPos relativePos = pos.relative(direction);

            if (containedFluid == Fluids.EMPTY) {
                // Pick up fluid
                BlockState blockState = level.getBlockState(pos);
                if (level.getFluidState(pos).isSource()) {
                    Fluid sourceFluid = level.getFluidState(pos).getType();

                    // Check if the fluid is one we can pick up
                    if (isValidFluid(sourceFluid)) {
                        if (level.mayInteract(player, pos) && player.mayUseItemAt(pos, direction, heldItem)) {
                            // Remove the fluid block
                            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 11);
                            level.gameEvent(player, GameEvent.FLUID_PICKUP, pos);

                            // Play appropriate sound
                            playPickupSound(sourceFluid, player, level);

                            // Get the appropriate filled bucket
                            ItemStack filledBucket = getFilledBucket(sourceFluid);
                            if (heldItem.getDamageValue() > 0) {
                                filledBucket.setDamageValue(heldItem.getDamageValue());
                            }

                            return InteractionResultHolder.sidedSuccess(filledBucket, level.isClientSide());
                        }
                    }
                }
            } else {
                // Place fluid
                if (level.mayInteract(player, pos) && player.mayUseItemAt(relativePos, direction, heldItem)) {
                    if (level.isEmptyBlock(relativePos)) {
                        // Place the appropriate fluid block
                        placeFluid(level, relativePos, containedFluid);
                        level.gameEvent(player, GameEvent.FLUID_PLACE, relativePos);

                        // Play appropriate sound
                        playEmptySound(containedFluid, player, level, relativePos);

                        // Return damaged empty bucket
                        ItemStack emptyBucket = new ItemStack(Items.WOODEN_BUCKET.get());
                        int newDamage = heldItem.getDamageValue() + 1;
                        if (newDamage >= heldItem.getMaxDamage()) {
                            return InteractionResultHolder.sidedSuccess(ItemStack.EMPTY, level.isClientSide());
                        }
                        emptyBucket.setDamageValue(newDamage);
                        return InteractionResultHolder.sidedSuccess(emptyBucket, level.isClientSide());
                    }
                }
            }
        }

        return InteractionResultHolder.fail(heldItem);
    }

    private boolean isValidFluid(Fluid fluid) {
        return fluid == Fluids.WATER ||
                fluid == Fluids.LAVA ||
                fluid == Fluids.FLOWING_WATER ||
                fluid == Fluids.FLOWING_LAVA;
    }

    private ItemStack getFilledBucket(Fluid fluid) {
        if (fluid == Fluids.WATER || fluid == Fluids.FLOWING_WATER) {
            return new ItemStack(Items.WOODEN_WATER_BUCKET.get());
        } else if (fluid == Fluids.LAVA || fluid == Fluids.FLOWING_LAVA) {
            return new ItemStack(Items.WOODEN_LAVA_BUCKET.get());
        }
        return new ItemStack(Items.WOODEN_BUCKET.get());
    }

    private void placeFluid(Level level, BlockPos pos, Fluid fluid) {
        if (fluid == Fluids.WATER) {
            level.setBlock(pos, Blocks.WATER.defaultBlockState(), 11);
        } else if (fluid == Fluids.LAVA) {
            level.setBlock(pos, Blocks.LAVA.defaultBlockState(), 11);
        }
    }

    private void playPickupSound(Fluid fluid, Player player, Level level) {
        if (fluid == Fluids.LAVA || fluid == Fluids.FLOWING_LAVA) {
            level.playSound(player, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.BUCKET_FILL_LAVA, SoundSource.PLAYERS, 1.0F, 1.0F);
        } else {
            level.playSound(player, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.BUCKET_FILL, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
    }

    private void playEmptySound(Fluid fluid, Player player, Level level, BlockPos pos) {
        if (fluid == Fluids.LAVA) {
            level.playSound(player, pos, SoundEvents.BUCKET_EMPTY_LAVA, SoundSource.BLOCKS, 1.0F, 1.0F);
        } else {
            level.playSound(player, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
    }

    public static BlockHitResult getPlayerPOVHitResult(Level level, Player player, ClipContext.Fluid fluidMode) {
        float f = player.getXRot();
        float f1 = player.getYRot();
        net.minecraft.world.phys.Vec3 vec3 = player.getEyePosition();
        float f2 = net.minecraft.util.Mth.cos(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f3 = net.minecraft.util.Mth.sin(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f4 = -net.minecraft.util.Mth.cos(-f * ((float)Math.PI / 180F));
        float f5 = net.minecraft.util.Mth.sin(-f * ((float)Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double reach = 5.0D;
        net.minecraft.world.phys.Vec3 vec31 = vec3.add((double)f6 * reach, (double)f5 * reach, (double)f7 * reach);
        return level.clip(new ClipContext(vec3, vec31, ClipContext.Block.OUTLINE, fluidMode, player));
    }
}