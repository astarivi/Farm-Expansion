package ovh.astarivi.farmexpansion.mixin;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import org.spongepowered.asm.mixin.Unique;
import ovh.astarivi.farmexpansion.block.farmland.FarmlandBlockEntity;
import ovh.astarivi.farmexpansion.manager.BlockManager;
import ovh.astarivi.farmexpansion.util.AdvancedCrop;
import ovh.astarivi.farmexpansion.util.Nutrient;


@Mixin(CropBlock.class)
public abstract class CropBlockMixin extends PlantBlock implements Fertilizable, AdvancedCrop {
    public CropBlockMixin(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(this.getAgeProperty(), 0));
    }

    @Shadow
    protected abstract IntProperty getAgeProperty();

    @Shadow
    public abstract int getMaxAge();

    @Shadow
    public abstract int getAge(BlockState state);

    @Shadow
    public abstract BlockState withAge(int age);

    @Override
    public boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOf(BlockManager.FARMLAND_BLOCK);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockEntity blockEntity = world.getBlockEntity(pos.down());
        int currentAge = this.getAge(state);

        if (currentAge >= this.getMaxAge() || !(blockEntity instanceof FarmlandBlockEntity) || world.getBaseLightLevel(pos, 0) < 9) return;

        if (((FarmlandBlockEntity) blockEntity).shouldGrow(soilExpanded$getGrowNutrient(), random)) {
            world.setBlockState(pos, this.withAge(currentAge + 1), Block.NOTIFY_LISTENERS);
        }
    }

    @Unique
    @Override
    public Nutrient soilExpanded$getGrowNutrient() {
        return Nutrient.NITROGEN;
    }
}
