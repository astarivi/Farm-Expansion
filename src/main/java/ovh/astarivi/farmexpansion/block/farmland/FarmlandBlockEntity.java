package ovh.astarivi.farmexpansion.block.farmland;

import net.fabricmc.fabric.api.blockview.v2.RenderDataBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import ovh.astarivi.farmexpansion.manager.BlockManager;
import ovh.astarivi.farmexpansion.util.Nutrient;

import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;


public class FarmlandBlockEntity extends BlockEntity implements GeoBlockEntity, RenderDataBlockEntity {
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    // First value is nutrient, second is moisture
    private static final float[] WEIGHTS = new float[] {0.6F, 0.4F};
    // NPKM as in Nitrogen, Phosphorus, Potassium and Moisture
    private int[] npkm = new int[] {
            50, 50, 50, 10
    };
    private int[] pendingNutrients = new int[] {
            0, 0, 0
    };

    public FarmlandBlockEntity(BlockPos pos, BlockState state) {
        super(BlockManager.FARMLAND_BLOCK_ENTITY, pos, state);
    }

    @Nullable
    public Object getRenderData() {
        return new FarmlandRenderData(npkm);
    }

    // NBT
    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putIntArray("npkm", npkm);
        nbt.putIntArray("pending_npkm", pendingNutrients);

        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        npkm = nbt.getIntArray("npkm");
        pendingNutrients = nbt.getIntArray("pending_npkm");
    }

    public boolean shouldGrow(Nutrient nutrient, Random random) {
        final int nutrientIndex = nutrient.getValue();
        final float scaledOutput = ((WEIGHTS[0] * npkm[nutrientIndex] / 100.0f) + (WEIGHTS[1] * npkm[3] / 100.0f)) * 0.2f;
        final boolean result = random.nextFloat() <= scaledOutput;

        if (result) {
            npkm[nutrientIndex] = Math.min(npkm[nutrientIndex] - 1, 0);
            markDirty();
        }

        return result;
    }

    public void randomTick(World world, BlockPos pos, BlockState state, Random random) {
        // Moisture changes
        int currentMoisture = calculateCurrentMoisture(world, pos);
        int storedMoisture = npkm[3];
        int randomRate = random.nextBetween(2, 6);

        if (currentMoisture != storedMoisture) {
            npkm[3] = (currentMoisture > storedMoisture)
                    ? Math.min(storedMoisture + randomRate, currentMoisture)
                    : Math.max(storedMoisture - randomRate, currentMoisture);
        }

        // Nutrients absorption
        int nutrientDelta = randomRate / 2;
        for (int i = 0; i < pendingNutrients.length; i++) {
            int pendingNutrient = pendingNutrients[i];
            int currentNutrient = npkm[i];

            if (pendingNutrient == 0 || currentNutrient == 100) continue;

            int amountToMove = Math.min(nutrientDelta, 100 - currentNutrient);

            currentNutrient += amountToMove;
            pendingNutrient -= amountToMove;

            npkm[i] = currentNutrient;
            pendingNutrients[i] = pendingNutrient;
        }

        markDirty();
        world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
    }

    protected int calculateCurrentMoisture(World world, BlockPos centerPos) {
        if (world.hasRain(centerPos.up())) return 100;

        double closestWaterSourceDistance = -1;

        for (BlockPos blockPos : BlockPos.iterate(centerPos.add(-2, 0, -2), centerPos.add(2, 0, 2))) {
            if (!world.getFluidState(blockPos).isIn(FluidTags.WATER)) continue;

            closestWaterSourceDistance = Math.sqrt(centerPos.getSquaredDistance(blockPos));

            if (closestWaterSourceDistance == 1) break;
        }

        return switch ((int) closestWaterSourceDistance) {
            case 1 -> 85;
            case 2 -> 60;
            case 3 -> 30;
            default -> 0;
        };
    }

    // Data sync
    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    // GeckoLib
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
